package com.aleksandrgenrikhs.nivkhalphabetcompose.di

import com.aleksandrgenrikhs.nivkhalphabetcompose.utils.AlphabetMediaPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object AppProvidesModule {
    @Provides
    @Singleton
    fun provideMediaPlayer(): AlphabetMediaPlayer = AlphabetMediaPlayer
}