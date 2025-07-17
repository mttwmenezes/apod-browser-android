package com.github.mttwmenezes.apodbrowser.infrastructure.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.mttwmenezes.apodbrowser.data.dao.BookmarksDao
import com.github.mttwmenezes.apodbrowser.data.model.Apod

@Database(entities = [Apod::class], version = 1)
abstract class BookmarksDatabase : RoomDatabase() {

    abstract fun dao(): BookmarksDao

    companion object {
        private const val NAME = "bookmarks_db"

        @Volatile private var INSTANCE: BookmarksDatabase? = null

        fun getInstance(context: Context): BookmarksDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, BookmarksDatabase::class.java, NAME)
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
