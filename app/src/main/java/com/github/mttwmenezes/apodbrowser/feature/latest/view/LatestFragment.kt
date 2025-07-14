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
import com.github.mttwmenezes.apodbrowser.feature.latest.view.feed.LatestFeedAdapter
import com.github.mttwmenezes.apodbrowser.feature.latest.view.feed.LatestFeedBuilder
import com.github.mttwmenezes.apodbrowser.feature.latest.view.feed.LatestFeedSpacingDecoration
import com.github.mttwmenezes.apodbrowser.feature.latest.viewmodel.LatestViewModel
import com.github.mttwmenezes.apodbrowser.feature.other.extension.hide
import com.github.mttwmenezes.apodbrowser.feature.other.extension.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LatestFragment : Fragment(), LatestFeedAdapter.Listener {

    private var _binding: FragmentLatestBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LatestViewModel by viewModels()

    private lateinit var feedAdapter: LatestFeedAdapter
    @Inject lateinit var feedBuilder: LatestFeedBuilder
    @Inject lateinit var feedSpacingDecoration: LatestFeedSpacingDecoration

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
        }
    }

    private fun onSuccess(apods: List<Apod>) = with(binding) {
        feedSwipeRefresh.isRefreshing = false
        feedRecyclerView.show()
        errorPlaceholder.root.hide()
        feedAdapter.submitList(feedBuilder.build(apods))
    }

    override fun onFeedItemClicked(id: String) {
        // TODO To be implemented
    }

    override fun onFeedItemOptionsClicked(id: String) {
        // TODO To be implemented
    }

    override fun onFeedItemExploreHintClicked() {
        // TODO To be implemented
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
