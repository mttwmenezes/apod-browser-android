package com.github.mttwmenezes.apodbrowser.infrastructure.observable

import androidx.annotation.MainThread

@MainThread
abstract class Observable<T> {

    private val _observers = hashSetOf<T>()
    val observers get() = _observers.toSet()

    fun addObserver(observer: T) {
        _observers.add(observer)
    }

    fun removeObserver(observer: T) {
        _observers.remove(observer)
    }

    protected inline fun notifyObservers(crossinline block: T.() -> Unit) {
        observers.forEach { block(it) }
    }
}
