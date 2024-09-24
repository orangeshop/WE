package com.we.di

import com.data.repository.BankRepository
import com.data.repository.CoupleRepository
import com.data.repository.DataStoreRepository
import com.data.repository.ScheduleRepository
import com.data.repository.SignRepository
import com.data.repositoryimpl.BankRepositoryImpl
import com.data.repositoryimpl.CoupleRepositoryImpl
import com.data.repositoryimpl.DataStoreRepositoryImpl
import com.data.repositoryimpl.ScheduleRepositoryImpl
import com.data.repositoryimpl.SignRepositoryImpl
import dagger.Binds
import dagger.Module
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
    fun bindsCoupleRepository(
        coupleRepositoryImpl: CoupleRepositoryImpl
    ): CoupleRepository

    @Singleton
    @Binds
    fun bindsDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): DataStoreRepository

    @Singleton
    @Binds
    fun bindsBankRepository(
        bankRepositoryImpl: BankRepositoryImpl
    ): BankRepository

    @Singleton
    @Binds
    fun bindsScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository
}