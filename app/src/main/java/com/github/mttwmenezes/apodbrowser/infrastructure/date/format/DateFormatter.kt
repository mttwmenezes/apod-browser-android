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
