package com.github.mttwmenezes.apodbrowser.infrastructure.date.format

import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date

interface DateFormatting {
    fun format(date: Date): String
}
