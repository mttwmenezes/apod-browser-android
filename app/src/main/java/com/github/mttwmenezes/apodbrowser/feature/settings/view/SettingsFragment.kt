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

package com.github.mttwmenezes.apodbrowser.feature.settings.view

import android.os.Bundle
import android.view.View
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.mttwmenezes.apodbrowser.BuildConfig
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.feature.settings.other.ImageCachePreference
import com.github.mttwmenezes.apodbrowser.infrastructure.storage.DirectoryUtils
import com.github.mttwmenezes.apodbrowser.infrastructure.storage.ImageCache
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var imageCacheOption: Preference

    @Inject lateinit var imageCachePreference: ImageCachePreference

    @Inject lateinit var messages: SettingsMessages

    @Inject lateinit var imageCache: ImageCache

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.app_settings, rootKey)
        imageCacheOption = findPreference(IMAGE_CACHE_KEY)!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureImageCacheOption()
        configureAppVersionOption()
    }

    private fun configureImageCacheOption() {
        imageCacheOption.summary = getString(
            R.string.preference_summary_image_cache,
            DirectoryUtils.sizeFormatted(imageCache.size)
        )
    }

    private fun configureAppVersionOption() {
        findPreference<Preference>(APP_VERSION_KEY)?.let { it.summary = BuildConfig.VERSION_NAME }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            IMAGE_CACHE_KEY -> handleImageCacheOptionClick()
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun handleImageCacheOptionClick() {
        if (imageCachePreference.alertDisplayed) {
            clearImageCache()
        } else {
            showImageCacheDialog()
        }
    }

    private fun clearImageCache() {
        imageCache.clear()
        configureImageCacheOption()
        messages.showImageCacheClearedMessage(requireView())
    }

    private fun showImageCacheDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setIcon(R.drawable.ic_storage)
            .setTitle(R.string.dialog_title_clear_image_cache)
            .setMessage(R.string.dialog_message_clear_image_cache)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                clearImageCache()
                imageCachePreference.alertDisplayed = true
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    companion object {
        private const val IMAGE_CACHE_KEY = "image_cache"
        private const val APP_VERSION_KEY = "app_version"
    }
}
