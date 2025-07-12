package com.github.mttwmenezes.apodbrowser.data.model

import java.io.Serializable

data class Apod(
    val title: String,
    val date: String,
    val url: String,
    val hdUrl: String?,
    val mediaType: String,
    val explanation: String,
    val thumbnailUrl: String?,
    val copyright: String?
) : Serializable {
    val isImage get() = mediaType == "image"
}
