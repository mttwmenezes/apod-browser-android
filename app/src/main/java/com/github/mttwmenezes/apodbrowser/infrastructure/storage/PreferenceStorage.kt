package com.github.mttwmenezes.apodbrowser.infrastructure.storage

import android.content.Context
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferenceStorage @Inject constructor(@ApplicationContext private val context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPreferences.edit()

    fun putBoolean(content: Pair<String, Boolean>) {
        editor.apply {
            val (key, value) = content
            putBoolean(key, value)
            apply()
        }
    }

    fun getBoolean(key: String, defValue: Boolean) = sharedPreferences.getBoolean(key, defValue)
}
