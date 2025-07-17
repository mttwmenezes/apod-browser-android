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
}
