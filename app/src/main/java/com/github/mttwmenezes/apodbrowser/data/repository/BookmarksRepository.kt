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

package com.github.mttwmenezes.apodbrowser.data.repository

import com.github.mttwmenezes.apodbrowser.data.dao.BookmarksDao
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class BookmarksRepository @Inject constructor(private val dao: BookmarksDao) {

    suspend fun add(apod: Apod) = dao.insert(apod)

    suspend fun remove(apod: Apod) = dao.delete(apod)

    suspend fun isBookmarked(apod: Apod) = dao.findByDate(apod.date) != null

    fun fetchAll() = dao.fetchAll()

    fun findByQuery(queryText: String) = dao.findByQuery(queryText)
}
