package com.github.mttwmenezes.apodbrowser.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.github.mttwmenezes.apodbrowser.data.model.Apod
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarksDao {
    @Insert
    suspend fun insert(apod: Apod)

    @Delete
    suspend fun delete(apod: Apod)

    @Query("SELECT * FROM apod WHERE date = :date")
    suspend fun findByDate(date: String): Apod?

    @Query("SELECT * FROM apod")
    fun fetchAll(): Flow<List<Apod>>

    @Query("SELECT * FROM apod WHERE title LIKE '%' || :queryText || '%'")
    fun findByQuery(queryText: String): Flow<List<Apod>>
}
