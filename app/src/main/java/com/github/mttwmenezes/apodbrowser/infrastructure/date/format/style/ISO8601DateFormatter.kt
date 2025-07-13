package com.github.mttwmenezes.apodbrowser.infrastructure.date.format.style

import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatting
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ISO8601DateFormatter @Inject constructor() : DateFormatting {
    override fun format(date: Date) = date.wrapped.format(DateTimeFormatter.ISO_DATE).orEmpty()
}
