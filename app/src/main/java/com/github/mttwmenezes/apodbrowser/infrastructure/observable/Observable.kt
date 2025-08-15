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
