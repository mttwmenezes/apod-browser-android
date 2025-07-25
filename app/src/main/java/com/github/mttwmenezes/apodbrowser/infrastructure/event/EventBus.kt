package com.github.mttwmenezes.apodbrowser.infrastructure.event

import androidx.annotation.MainThread
import com.github.mttwmenezes.apodbrowser.infrastructure.observable.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@MainThread
class EventBus @Inject constructor() : Observable<EventObserver>() {

    fun post(event: Any) {
        notifyObservers { onEvent(event) }
    }
}
