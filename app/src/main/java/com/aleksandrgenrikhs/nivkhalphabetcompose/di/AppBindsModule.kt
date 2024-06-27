package com.aleksandrgenrikhs.nivkhalphabetcompose.di

import com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository.AlphabetRepositoryImpl
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository.PrefRepositoryImpl
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.model.repository.PrefRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface AppBindsModule {

    @Binds
    @Singleton
    fun bindAlphabetRepository(impl: AlphabetRepositoryImpl): AlphabetRepository

    @Binds
    @Singleton
    fun bindSharedPreferencesRepository(impl: PrefRepositoryImpl): PrefRepository
}