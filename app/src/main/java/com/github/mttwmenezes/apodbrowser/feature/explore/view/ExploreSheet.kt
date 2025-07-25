package com.github.mttwmenezes.apodbrowser.feature.explore.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.SheetExploreBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ExploreSheet : BottomSheetDialogFragment() {

    private var _binding: SheetExploreBinding? = null
    private val binding get() = _binding!!

    private lateinit var apod: Apod

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apod = requireArguments().getSerializable(ARG_APOD, Apod::class.java) as Apod
    }

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
        binding.closeButton.setOnClickListener { dismissNow() }
    }

    companion object {
        private const val ARG_APOD = "apod"

        fun newInstance(apod: Apod) = ExploreSheet().apply {
            arguments = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
        }
    }
}
