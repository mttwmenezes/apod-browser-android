package com.github.mttwmenezes.apodbrowser.infrastructure.event

import androidx.annotation.MainThread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@MainThread
class EventPublisher @Inject constructor(private val eventBus: EventBus) {

    fun publish(event: Any) {
        eventBus.post(event)
    }
}
