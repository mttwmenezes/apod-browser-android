package com.github.mttwmenezes.apodbrowser.infrastructure.event

interface EventObserver {
    fun onEvent(event: Any)
}
