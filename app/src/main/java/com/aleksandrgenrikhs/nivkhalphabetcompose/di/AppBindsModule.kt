package com.aleksandrgenrikhs.nivkhalphabetcompose.di

import com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository.AlphabetRepositoryImpl
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository.CertificatePdfRepositoryImpl
import com.aleksandrgenrikhs.nivkhalphabetcompose.data.repository.PrefRepositoryImpl
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.AlphabetRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.CertificatePdfRepository
import com.aleksandrgenrikhs.nivkhalphabetcompose.domain.repository.PrefRepository
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

    @Binds
    @Singleton
    fun bindCertificateRepository(impl: CertificatePdfRepositoryImpl): CertificatePdfRepository
}