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

package com.github.mttwmenezes.apodbrowser.feature.latest.view.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.github.mttwmenezes.apodbrowser.feature.other.event.DatePicked
import com.github.mttwmenezes.apodbrowser.infrastructure.event.EventPublisher
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class DatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    @Inject lateinit var eventPublisher: EventPublisher

    private val calendar = Calendar.getInstance()

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(year, month, dayOfMonth)
        eventPublisher.publish(DatePicked(calendar.timeInMillis))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(
            requireContext(),
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = FIRST_APOD_DATE_MILLIS
            datePicker.maxDate = System.currentTimeMillis()
        }
    }

    companion object {
        private const val FIRST_APOD_DATE_MILLIS = 803271600000L
    }
}
