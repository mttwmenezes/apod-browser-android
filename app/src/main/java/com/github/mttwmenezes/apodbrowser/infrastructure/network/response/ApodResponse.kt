package com.github.mttwmenezes.apodbrowser.infrastructure.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApodResponse(
    val title: String? = null,
    val date: String? = null,
    val url: String? = null,
    @SerialName("hdurl")
    val hdUrl: String? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    val explanation: String? = null,
    @SerialName("thumbnail_url")
    val thumbnailUrl: String? = null,
    val copyright: String? = null,
    @SerialName("service_version")
    val serviceVersion: String? = null
)
