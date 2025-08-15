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

package com.github.mttwmenezes.apodbrowser.infrastructure.date.format.style

import android.content.Context
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatting
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AgoDateStyleFormatter @Inject constructor(
    @ApplicationContext private val context: Context
) : DateFormatting {

    override fun format(date: Date): String {
        if (date.isToday) return context.getString(R.string.date_today)

        val intervalInDays = date.intervalBetween(Date.now(), Date.Interval.Days)

        return when {
            intervalInDays > YEAR -> yearsFormatted(date)
            intervalInDays in monthsRange -> monthsFormatted(date)
            intervalInDays in weeksRange -> weeksFormatted(date)
            else -> daysFormatted(date)
        }
    }

    private val monthsRange get() = MONTH..<YEAR

    private val weeksRange get() = WEEK..<MONTH

    private fun yearsFormatted(date: Date): String {
        val years = date.intervalBetween(Date.now(), Date.Interval.Years)
        return if (years == 1L) {
            context.getString(R.string.date_one_year_ago)
        } else {
            context.getString(R.string.date_years_ago_format, years)
        }
    }

    private fun monthsFormatted(date: Date): String {
        val months = date.intervalBetween(Date.now(), Date.Interval.Months)
        return if (months == 1L) {
            context.getString(R.string.date_one_month_ago)
        } else {
            context.getString(R.string.date_months_ago_format, months)
        }
    }

    private fun weeksFormatted(date: Date): String {
        val weeks = date.intervalBetween(Date.now(), Date.Interval.Weeks)
        return if (weeks == 1L) {
            context.getString(R.string.date_one_week_ago)
        } else {
            context.getString(R.string.date_weeks_ago_format, weeks)
        }
    }

    private fun daysFormatted(date: Date): String {
        val days = date.intervalBetween(Date.now(), Date.Interval.Days)
        return if (days == 1L) {
            context.getString(R.string.date_one_day_ago)
        } else {
            context.getString(R.string.date_days_ago_format, days)
        }
    }

    companion object {
        private const val YEAR = 365
        private const val MONTH = 30
        private const val WEEK = 7
    }
}
