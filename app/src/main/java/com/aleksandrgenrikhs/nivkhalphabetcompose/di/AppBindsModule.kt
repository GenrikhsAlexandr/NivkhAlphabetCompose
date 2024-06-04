package com.aleksandrgenrikhs.nivkhalphabetcompose.di

import com.aleksandrgenrikhs.nivkhalphabet.domain.repository.FirstTaskRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository.FirstTaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
interface AppBindsModule {

   @Binds
    @Singleton
    fun bindFirstTaskRepository(impl: FirstTaskRepositoryImpl): FirstTaskRepository

    /* @Binds
    @Singleton
    fun bindSharedPreferencesRepository(impl: SharedPreferencesRepositoryImpl): SharedPreferencesRepository
*/
}