/*
 * Copyright 2025 Matheus Menezes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mttwmenezes.apodbrowser.feature.bookmarks.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.FragmentBookmarksBinding
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed.BookmarksFeedAdapter
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed.BookmarksFeedBuilder
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.view.feed.BookmarksFeedSpacingDecoration
import com.github.mttwmenezes.apodbrowser.feature.bookmarks.viewmodel.BookmarksViewModel
import com.github.mttwmenezes.apodbrowser.feature.detail.view.DetailActivity
import com.github.mttwmenezes.apodbrowser.feature.home.TopLevelDestination
import com.github.mttwmenezes.apodbrowser.feature.other.delegate.HomeLayoutDelegate
import com.github.mttwmenezes.apodbrowser.feature.other.event.SearchBarQueryTextChanged
import com.github.mttwmenezes.apodbrowser.feature.other.event.TopLevelDestinationChanged
import com.github.mttwmenezes.apodbrowser.feature.other.extension.hide
import com.github.mttwmenezes.apodbrowser.feature.other.extension.show
import com.github.mttwmenezes.apodbrowser.feature.other.sheet.FeedItemOptionsSheet
import com.github.mttwmenezes.apodbrowser.infrastructure.event.EventObserver
import com.github.mttwmenezes.apodbrowser.infrastructure.event.EventSubscriber
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BookmarksFragment : Fragment(), BookmarksFeedAdapter.Listener, EventObserver {

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BookmarksViewModel by viewModels()

    private lateinit var feedAdapter: BookmarksFeedAdapter
    @Inject lateinit var feedBuilder: BookmarksFeedBuilder
    @Inject lateinit var feedSpacingDecoration: BookmarksFeedSpacingDecoration

    @Inject lateinit var messages: BookmarkMessages
    @Inject lateinit var homeLayoutDelegate: HomeLayoutDelegate
    @Inject lateinit var eventSubscriber: EventSubscriber

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUi()
        observeUiState()
    }

    private fun configureUi() {
        feedAdapter = BookmarksFeedAdapter(listener = this)
        binding.feedRecyclerView.apply {
            adapter = feedAdapter
            setHasFixedSize(false)
            addItemDecoration(feedSpacingDecoration)
            layoutManager = StaggeredGridLayoutManager(
                COMPACT_VERTICAL_SPAN_COUNT,
                StaggeredGridLayoutManager.VERTICAL
            ).apply {
                gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
            }
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when {
                        uiState.isEmpty -> onEmptyBookmarks()
                        uiState.isFailure -> onFailure()
                        uiState.isSuccess -> onSuccess(uiState.bookmarks)
                        uiState.isSearchEmpty -> onEmptySearch()
                    }
                }
            }
        }
    }

    private fun onEmptyBookmarks() = with(binding) {
        feedRecyclerView.hide()
        emptyPlaceholder.root.show()
        resetEmptyPlaceholderVerticalPosition()
        emptyPlaceholder.content.y -= homeLayoutDelegate.navigationBar.height
    }

    private fun resetEmptyPlaceholderVerticalPosition() {
        binding.emptyPlaceholder.content.y = 0f
    }

    private fun onFailure() = with(binding) {
        feedRecyclerView.isVisible = !emptyPlaceholder.root.isVisible
        emptyPlaceholder.root.isVisible = !feedRecyclerView.isVisible
        messages.showUnexpectedErrorOccurredMessage(
            root,
            anchor = homeLayoutDelegate.navigationBar
        )
    }

    private fun onSuccess(bookmarks: List<Apod>) = with(binding) {
        feedRecyclerView.show()
        emptyPlaceholder.root.hide()
        feedAdapter.submitList(feedBuilder.build(bookmarks))
    }

    private fun onEmptySearch() = with(binding) {
        feedRecyclerView.hide()
        emptyPlaceholder.apply {
            root.show()
            messageLabel.text = getString(R.string.bookmarks_empty_search_message)
            resetEmptyPlaceholderVerticalPosition()
            content.y -= homeLayoutDelegate.navigationBar.height
        }
    }

    override fun onFeedItemClicked(id: String) {
        viewModel.findBookmarkBy(id)?.let { DetailActivity.start(requireContext(), it) }
    }

    override fun onFeedItemLongClicked(id: String) {
        viewModel.findBookmarkBy(id)?.let {
            FeedItemOptionsSheet.newInstance(it).show(childFragmentManager, null)
        }
    }

    override fun onEvent(event: Any) {
        when (event) {
            is SearchBarQueryTextChanged -> handleSearchBarQueryTextChangedEvent(event.queryText)
            is TopLevelDestinationChanged -> handleTopLevelDestinationChanged(event)
        }
    }

    private fun handleSearchBarQueryTextChangedEvent(queryText: String) {
        if (queryText.isNotEmpty()) {
            viewModel.findBookmarksBy(queryText)
        } else {
            clearSearch()
        }
    }

    private fun clearSearch() {
        viewModel.fetchBookmarks()
    }

    private fun handleTopLevelDestinationChanged(event: TopLevelDestinationChanged) {
        if (isOtherTopLevelDestination(event.destination)) clearSearch()
    }

    private fun isOtherTopLevelDestination(destination: TopLevelDestination) = listOf(
        TopLevelDestination.Latest,
        TopLevelDestination.Settings
    ).find { it == destination } != null

    override fun onStart() {
        super.onStart()
        eventSubscriber.subscribe(this)
    }

    override fun onStop() {
        super.onStop()
        eventSubscriber.unsubscribe(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val COMPACT_VERTICAL_SPAN_COUNT = 2
    }
}
