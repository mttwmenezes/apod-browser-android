package com.github.mttwmenezes.apodbrowser.infrastructure.di.module

import android.content.Context
import com.github.mttwmenezes.apodbrowser.feature.home.view.HomeActivity
import com.github.mttwmenezes.apodbrowser.feature.other.delegate.HomeLayoutDelegate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    fun provideHomeLayoutDelegate(@ActivityContext context: Context) =
        context as HomeActivity as HomeLayoutDelegate
}
