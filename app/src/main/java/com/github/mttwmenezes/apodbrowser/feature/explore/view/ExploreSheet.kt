package com.github.mttwmenezes.apodbrowser.feature.explore.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mttwmenezes.apodbrowser.databinding.SheetExploreBinding
import com.github.mttwmenezes.apodbrowser.feature.other.event.ExploreOptionClicked
import com.github.mttwmenezes.apodbrowser.infrastructure.event.EventPublisher
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ExploreSheet : BottomSheetDialogFragment() {

    private var _binding: SheetExploreBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var eventPublisher: EventPublisher

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SheetExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            closeButton.setOnClickListener { dismissNow() }
            randomPickButton.setOnClickListener {
                eventPublisher.publish(ExploreOptionClicked.RandomPick)
                dismissNow()
            }
        }
    }
}
