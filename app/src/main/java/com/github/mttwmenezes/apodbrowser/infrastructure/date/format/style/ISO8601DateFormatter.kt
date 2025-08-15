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

import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatting
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ISO8601DateFormatter @Inject constructor() : DateFormatting {
    override fun format(date: Date) = date.wrapped.format(DateTimeFormatter.ISO_DATE).orEmpty()
}
