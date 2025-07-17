package com.github.mttwmenezes.apodbrowser.infrastructure.date.format.style

import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatting
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FullStyleDateFormatter @Inject constructor() : DateFormatting {
    override fun format(date: Date) = date.wrapped.format(
        DateTimeFormatter.ofPattern("MMMM d, yyyy")
    ).orEmpty()
}
