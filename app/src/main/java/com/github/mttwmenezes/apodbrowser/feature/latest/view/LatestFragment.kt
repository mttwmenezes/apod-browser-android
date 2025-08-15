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

package com.github.mttwmenezes.apodbrowser.feature.latest.view

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
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.FragmentLatestBinding
import com.github.mttwmenezes.apodbrowser.feature.detail.view.DetailActivity
import com.github.mttwmenezes.apodbrowser.feature.explore.view.ExploreSheet
import com.github.mttwmenezes.apodbrowser.feature.latest.view.dialog.DatePickerDialog
import com.github.mttwmenezes.apodbrowser.feature.latest.view.feed.LatestFeedAdapter
import com.github.mttwmenezes.apodbrowser.feature.latest.view.feed.LatestFeedBuilder
import com.github.mttwmenezes.apodbrowser.feature.latest.view.feed.LatestFeedSpacingDecoration
import com.github.mttwmenezes.apodbrowser.feature.latest.viewmodel.LatestViewModel
import com.github.mttwmenezes.apodbrowser.feature.other.delegate.HomeLayoutDelegate
import com.github.mttwmenezes.apodbrowser.feature.other.event.DatePicked
import com.github.mttwmenezes.apodbrowser.feature.other.event.ExploreActionClicked
import com.github.mttwmenezes.apodbrowser.feature.other.event.ExploreOptionClicked
import com.github.mttwmenezes.apodbrowser.feature.other.event.RefreshActionClicked
import com.github.mttwmenezes.apodbrowser.feature.other.extension.hide
import com.github.mttwmenezes.apodbrowser.feature.other.extension.show
import com.github.mttwmenezes.apodbrowser.feature.other.sheet.FeedItemOptionsSheet
import com.github.mttwmenezes.apodbrowser.infrastructure.event.EventObserver
import com.github.mttwmenezes.apodbrowser.infrastructure.event.EventSubscriber
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LatestFragment : Fragment(), LatestFeedAdapter.Listener, EventObserver {

    private var _binding: FragmentLatestBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LatestViewModel by viewModels()

    private lateinit var feedAdapter: LatestFeedAdapter
    @Inject lateinit var feedBuilder: LatestFeedBuilder
    @Inject lateinit var feedSpacingDecoration: LatestFeedSpacingDecoration

    @Inject lateinit var eventSubscriber: EventSubscriber
    @Inject lateinit var messages: LatestMessages
    @Inject lateinit var homeLayoutDelegate: HomeLayoutDelegate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLatestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureUi()
        observeUiState()
    }

    private fun configureUi() {
        feedAdapter = LatestFeedAdapter(listener = this)
        binding.feedSwipeRefresh.setOnRefreshListener { viewModel.fetchLatest() }
        binding.feedRecyclerView.apply {
            adapter = feedAdapter
            setHasFixedSize(true)
            addItemDecoration(feedSpacingDecoration)
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when {
                        uiState.isLoading -> onLoading()
                        uiState.isFailure -> onFailure()
                        uiState.isSuccess -> onSuccess(uiState.apods)
                    }
                }
            }
        }
    }

    private fun onLoading() = with(binding) {
        feedSwipeRefresh.isRefreshing = true
        feedRecyclerView.isVisible = !errorPlaceholder.root.isVisible
        errorPlaceholder.root.isVisible = !feedRecyclerView.isVisible
    }

    private fun onFailure() = with(binding) {
        feedSwipeRefresh.isRefreshing = false
        feedRecyclerView.hide()
        errorPlaceholder.apply {
            root.show()
            tryAgainButton.setOnClickListener { viewModel.fetchLatest() }
            resetErrorPlaceholderVerticalPosition()
            content.y -= homeLayoutDelegate.navigationBar.height
        }
    }

    private fun resetErrorPlaceholderVerticalPosition() {
        binding.errorPlaceholder.content.y = 0f
    }

    private fun onSuccess(apods: List<Apod>) = with(binding) {
        feedSwipeRefresh.isRefreshing = false
        feedRecyclerView.show()
        errorPlaceholder.root.hide()
        feedAdapter.submitList(feedBuilder.build(apods))
    }

    override fun onFeedItemClicked(id: String) {
        viewModel.findApodBy(id)?.let { DetailActivity.start(requireContext(), it) }
    }

    override fun onFeedItemOptionsClicked(id: String) {
        viewModel.findApodBy(id)?.let {
            FeedItemOptionsSheet.newInstance(it).show(childFragmentManager, null)
        }
    }

    override fun onFeedItemExploreHintClicked() {
        showExploreSheet()
    }

    override fun onEvent(event: Any) {
        when (event) {
            is ExploreActionClicked -> showExploreSheet()
            is ExploreOptionClicked -> handleExploreOptionClicked(event)
            is DatePicked -> fetchApodFromDate(event.dateInMillis)
            is RefreshActionClicked -> viewModel.fetchLatest()
        }
    }

    private fun showExploreSheet() {
        ExploreSheet().show(childFragmentManager, null)
    }

    private fun handleExploreOptionClicked(event: ExploreOptionClicked) {
        when (event) {
            ExploreOptionClicked.RandomPick -> fetchRandomApod()
            ExploreOptionClicked.Calendar -> showDatePickerDialog()
        }
    }

    private fun fetchRandomApod() = with(binding) {
        feedSwipeRefresh.isRefreshing = true
        viewModel.fetchRandom { apod ->
            feedSwipeRefresh.isRefreshing = false
            apod?.let {
                DetailActivity.start(requireContext(), it)
            } ?: run {
                showUnexpectedErrorMessage()
            }
        }
    }

    private fun showUnexpectedErrorMessage() {
        messages.showUnexpectedErrorMessage(
            binding.root,
            anchor = homeLayoutDelegate.navigationBar
        )
    }

    private fun showDatePickerDialog() {
        DatePickerDialog().show(childFragmentManager, null)
    }

    private fun fetchApodFromDate(dateInMillis: Long) = with(binding) {
        feedSwipeRefresh.isRefreshing = true
        viewModel.fetchFromDate(dateInMillis) { apod ->
            feedSwipeRefresh.isRefreshing = false
            apod?.let {
                DetailActivity.start(requireContext(), it)
            } ?: run {
                showUnexpectedErrorMessage()
            }
        }
    }

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
}
