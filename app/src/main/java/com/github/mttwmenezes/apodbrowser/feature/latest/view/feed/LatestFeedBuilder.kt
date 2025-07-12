package com.github.mttwmenezes.apodbrowser.feature.latest.view.feed

import com.github.mttwmenezes.apodbrowser.data.model.Apod
import com.github.mttwmenezes.apodbrowser.feature.latest.model.LatestFeedItem
import com.github.mttwmenezes.apodbrowser.feature.other.extension.capitalized
import com.github.mttwmenezes.apodbrowser.infrastructure.date.Date
import com.github.mttwmenezes.apodbrowser.infrastructure.date.format.DateFormatter
import javax.inject.Inject

class LatestFeedBuilder @Inject constructor(private val dateFormatter: DateFormatter) {

    fun build(apods: List<Apod>) = buildList {
        addAll(apods.map { apodFeedItem(it) })
        add(LatestFeedItem.ExploreHint)
    }

    private fun apodFeedItem(apod: Apod) = LatestFeedItem.Apod(
        itemId = apod.date,
        imageUrl = if (apod.isImage) apod.url else apod.thumbnailUrl,
        title = apod.title,
        mediaType = apod.mediaType.capitalized(),
        date = dateFormatter.format(Date.parse(apod.date), DateFormatter.Style.Ago)
    )
}
