package com.we.di

import com.data.repository.DataStoreRepository
import com.data.repository.SignRepository
import com.data.repositoryimpl.DataStoreRepositoryImpl
import com.data.repositoryimpl.SignRepositoryImpl
import com.data.util.TokenProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @Singleton
    @Binds
    fun bindsSignRepository(
        signRepositoryImpl: SignRepositoryImpl
    ): SignRepository


    @Singleton
    @Binds
    fun bindsDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository
}