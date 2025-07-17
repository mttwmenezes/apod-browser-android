package com.github.mttwmenezes.apodbrowser.infrastructure.date.format

import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.style.AgoDateStyleFormatter
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.style.FullStyleDateFormatter
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.style.ISO8601DateFormatter
import javax.inject.Inject

class DateFormatter @Inject constructor(
    private val agoStyleFormatter: AgoDateStyleFormatter,
    private val iso8601DateFormatter: ISO8601DateFormatter,
    private val fullStyleFormatter: FullStyleDateFormatter
) {
    fun format(date: Date, style: Style) = when (style) {
        Style.Ago -> agoStyleFormatter.format(date)
        Style.Iso -> iso8601DateFormatter.format(date)
        Style.Full -> fullStyleFormatter.format(date)
    }

    enum class Style {
        Ago,
        Iso,
        Full
    }
}
