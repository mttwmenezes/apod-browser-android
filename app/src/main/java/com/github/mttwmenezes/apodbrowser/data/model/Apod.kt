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

    val condensedDate
        get() = buildString {
            val (year, month, day) = date.split("-")
            append(year.takeLast(2))
            append(month)
            append(day)
        }
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
