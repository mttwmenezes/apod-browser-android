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

package com.github.mttwmenezes.apodbrowser.infrastructure.date

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class Date private constructor(val wrapped: LocalDate) {

    val isToday get() = wrapped.atStartOfDay() == LocalDate.now().atStartOfDay()

    fun intervalBetween(other: Date, interval: Interval) = when (interval) {
        Interval.Days -> ChronoUnit.DAYS.between(wrapped, other.wrapped)
        Interval.Weeks -> ChronoUnit.WEEKS.between(wrapped, other.wrapped)
        Interval.Months -> ChronoUnit.MONTHS.between(wrapped, other.wrapped)
        Interval.Years -> ChronoUnit.YEARS.between(wrapped, other.wrapped)
    }

    fun minusWeeks(count: Long) = Date(LocalDate.now().minusWeeks(count))

    enum class Interval {
        Days,
        Weeks,
        Months,
        Years
    }

    companion object {
        fun now() = Date(LocalDate.now())

        fun parse(raw: String) = Date(LocalDate.parse(raw))

        fun parse(dateInMillis: Long) = Date(
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dateInMillis),
                ZoneId.systemDefault()
            ).toLocalDate()
        )
    }
}
