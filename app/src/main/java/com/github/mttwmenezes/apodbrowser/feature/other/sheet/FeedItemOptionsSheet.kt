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

package com.github.mttwmenezes.apodbrowser.feature.other.sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil3.load
import coil3.request.crossfade
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.databinding.SheetFeedItemOptionsBinding
import com.github.mttwmenezes.apodbrowser.feature.detail.view.DetailActivity
import com.github.mttwmenezes.apodbrowser.feature.image.view.ImageDetailActivity
import com.github.mttwmenezes.apodbrowser.feature.other.extension.capitalized
import com.github.mttwmenezes.apodbrowser.feature.other.extension.openWebPage
import com.github.mttwmenezes.apodbrowser.feature.other.extension.shareUrl
import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedItemOptionsSheet : BottomSheetDialogFragment() {

    private var _binding: SheetFeedItemOptionsBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var dateFormatter: DateFormatter

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
        _binding = SheetFeedItemOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureInfoSection()
        configureOptionsSection()
    }

    private fun configureInfoSection() = with(binding) {
        image.load(imageUrl) { crossfade(true) }
        titleLabel.text = apod.title
        mediaTypeLabel.text = apod.mediaType.capitalized()
        dateLabel.text = dateFormatter.format(Date.parse(apod.date), DateFormatter.Style.Full)
    }

    private val imageUrl
        get() = if (apod.isImage) apod.url else apod.thumbnailUrl

    private fun configureOptionsSection() = with(binding) {
        openLabel.setOnClickListener {
            DetailActivity.start(requireContext(), apod)
            dismissNow()
        }
        openInBrowserLabel.setOnClickListener {
            requireContext().openWebPage(getString(R.string.apod_url_format, apod.condensedDate))
            dismissNow()
        }
        openImageLabel.setOnClickListener {
            ImageDetailActivity.start(requireContext(), apod)
            dismissNow()
        }
        shareLabel.setOnClickListener {
            requireContext().shareUrl(getString(R.string.apod_url_format, apod.condensedDate))
            dismissNow()
        }
    }

    companion object {
        private const val ARG_APOD = "apod"

        fun newInstance(apod: Apod) = FeedItemOptionsSheet().apply {
            arguments = Bundle(1).apply { putSerializable(ARG_APOD, apod) }
        }
    }
}
