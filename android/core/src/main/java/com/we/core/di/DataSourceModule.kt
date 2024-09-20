package com.we.di

import com.data.api.SignApi
import com.data.datasource.CoupleDataSource
import com.data.datasource.SignDataSource
import com.data.datasourceimpl.CoupleDataSourceImpl
import com.data.datasourceimpl.SignDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Singleton
    @Binds
    fun bindsSignDataSource(
        signDataSourceImpl: SignDataSourceImpl
    ) : SignDataSource


    @Singleton
    @Binds
    fun bindsCoupleDataSource(
        coupleDataSourceImpl: CoupleDataSourceImpl
    ) : CoupleDataSource
}