package com.aleksandrgenrikhs.nivkhalphabetcompose.di

import android.app.Application
import android.content.Context
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.AlphabetMediaPlayer
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.LazyGridScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.LazyListScrollableState
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.ScrollableState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object AppProvidesModule {

    @Provides
    @Singleton
    fun provideMediaPlayer(): AlphabetMediaPlayer = AlphabetMediaPlayer

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext app: Context): Context = app as Application

    @Provides
    @Singleton
    fun provideLazyListScrollableState(state: LazyListState): ScrollableState =
        LazyListScrollableState(state)

    @Provides
    @Singleton
    fun provideLazyGridScrollableState(state: LazyGridState): ScrollableState =
        LazyGridScrollableState(state)
}