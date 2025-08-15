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

package com.github.mttwmenezes.apodbrowser.feature.detail.view.sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.SheetImageCopyrightBinding
import com.github.mttwmenezes.apodbrowser.feature.other.extension.newLineTrimmed
import com.github.mttwmenezes.apodbrowser.feature.other.extension.openWebPage
import com.github.mttwmenezes.apodbrowser.feature.other.extension.setClickableSpan
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImageCopyrightSheet : BottomSheetDialogFragment() {

    private var _binding: SheetImageCopyrightBinding? = null
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
        _binding = SheetImageCopyrightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            closeButton.setOnClickListener { dismissNow() }
            copyrightLabel.text = apod.copyright.orEmpty().newLineTrimmed()
            copyrightHintLabel.setClickableSpan(
                text = getString(R.string.image_copyright_hint_text),
                clickableText = getString(R.string.mage_copyright_hint_clickable_text),
                onSpanClickListener = {
                    requireContext().openWebPage(
                        getString(R.string.apod_url_format, apod.condensedDate)
                    )
                }
            )
        }
    }

    companion object {
        private const val ARG_APOD = "apod"

        fun newInstance(apod: Apod) = ImageCopyrightSheet().apply {
            arguments = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
        }
    }
}
