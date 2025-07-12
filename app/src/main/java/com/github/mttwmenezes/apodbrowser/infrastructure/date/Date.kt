package com.github.mttwmenezes.apodbrowser.infrastructure.date

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Date private constructor(val wrapped: LocalDate) {

    val isToday get() = wrapped.atStartOfDay() == LocalDate.now().atStartOfDay()

    fun intervalBetween(other: Date, interval: Interval) = when (interval) {
        Interval.Days -> ChronoUnit.DAYS.between(wrapped, other.wrapped)
        Interval.Weeks -> ChronoUnit.WEEKS.between(wrapped, other.wrapped)
        Interval.Months -> ChronoUnit.MONTHS.between(wrapped, other.wrapped)
        Interval.Years -> ChronoUnit.YEARS.between(wrapped, other.wrapped)
    }

    enum class Interval {
        Days,
        Weeks,
        Months,
        Years
    }

    companion object {
        fun now() = Date(LocalDate.now())

        fun parse(raw: String) = Date(LocalDate.parse(raw))
    }
}
