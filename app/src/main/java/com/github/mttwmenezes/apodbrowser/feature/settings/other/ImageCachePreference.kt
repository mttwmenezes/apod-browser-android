package com.github.mttwmenezes.apodbrowser.feature.settings.other

import com.github.mttwmenezes.apodbrowser.infrastructure.storage.PreferenceStorage
import javax.inject.Inject

class ImageCachePreference @Inject constructor(private val storage: PreferenceStorage) {

    var alertDisplayed: Boolean
        get() = storage.getBoolean(ALERT_DISPLAYED_KEY, defValue = false)
        set(value) = storage.putBoolean(ALERT_DISPLAYED_KEY to value)

    companion object {
        private const val ALERT_DISPLAYED_KEY = "alert_displayed"
    }
}
