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

package com.github.mttwmenezes.apodbrowser.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.mttwmenezes.apodbrowser.feature.other.extension.newLineTrimmed
import com.github.mttwmenezes.apodbrowser.infrastructure.network.response.ApodResponse
import java.io.Serializable

@Entity
data class Apod(
    val title: String,
    @PrimaryKey val date: String,
    val url: String,
    val hdUrl: String?,
    val mediaType: String,
    val explanation: String,
    val thumbnailUrl: String?,
    val copyright: String?
) : Serializable {
    val isImage get() = mediaType == "image"
    val isVideo get() = mediaType == "video"

    val hasHdImage
        get() = !hdUrl.isNullOrBlank() && hdUrl != url

    val condensedDate
        get() = buildString {
            val (year, month, day) = date.split("-")
            append(year.takeLast(2))
            append(month)
            append(day)
        }

    val imageFilename: String
        get() = "APOD_$condensedDate"
}

fun ApodResponse.toApod() = Apod(
    title = title.orEmpty().newLineTrimmed(),
    date = date.orEmpty(),
    url = url.orEmpty(),
    hdUrl = hdUrl,
    mediaType = mediaType.orEmpty(),
    explanation = explanation.orEmpty().newLineTrimmed(),
    thumbnailUrl = thumbnailUrl,
    copyright = copyright
)
