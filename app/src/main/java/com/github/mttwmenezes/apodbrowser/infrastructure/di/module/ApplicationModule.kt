package com.github.mttwmenezes.apodbrowser.infrastructure.di.module

import android.content.Context
import com.github.mttwmenezes.apodbrowser.infrastructure.database.BookmarksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideBookmarksDatabase(@ApplicationContext context: Context) =
        BookmarksDatabase.getInstance(context)

    @Provides
    fun provideBookmarksDao(database: BookmarksDatabase) = database.dao()
}
