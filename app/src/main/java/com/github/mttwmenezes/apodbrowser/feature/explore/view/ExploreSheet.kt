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

package com.github.mttwmenezes.apodbrowser.feature.explore.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.databinding.SheetExploreBinding
import com.github.mttwmenezes.apodbrowser.feature.other.event.ExploreOptionClicked
import com.github.mttwmenezes.apodbrowser.feature.other.extension.openWebPage
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
            calendarButton.setOnClickListener {
                eventPublisher.publish(ExploreOptionClicked.Calendar)
                dismissNow()
            }
            archiveLabel.setOnClickListener {
                requireContext().openWebPage(getString(R.string.archive_url))
            }
        }
    }
}
