package com.aleksandrgenrikhs.nivkhalphabetcompose.di

import android.app.Application
import android.content.Context
import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.AlphabetMediaPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object AppProvidesModule {

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json { ignoreUnknownKeys = true }
    }

    @Provides
    @Singleton
    fun provideMediaPlayer(): AlphabetMediaPlayer = AlphabetMediaPlayer

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext app: Context): Context = app as Application
}