package com.github.mttwmenezes.apodbrowser.infrastructure.event

import androidx.annotation.MainThread
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@MainThread
class EventSubscriber @Inject constructor(private val eventBus: EventBus) {

    fun subscribe(observer: EventObserver) {
        eventBus.addObserver(observer)
    }

    fun unsubscribe(observer: EventObserver) {
        eventBus.removeObserver(observer)
    }
}
