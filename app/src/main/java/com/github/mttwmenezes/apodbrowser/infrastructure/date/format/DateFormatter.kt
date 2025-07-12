package com.github.mttwmenezes.apodbrowser.infrastructure.date.format

import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.style.AgoDateStyleFormatter
import javax.inject.Inject

class DateFormatter @Inject constructor(
    private val agoStyleFormatter: AgoDateStyleFormatter
) {
    fun format(date: Date, style: Style) = when (style) {
        Style.Ago -> agoStyleFormatter.format(date)
    }

    enum class Style {
        Ago
    }
}
