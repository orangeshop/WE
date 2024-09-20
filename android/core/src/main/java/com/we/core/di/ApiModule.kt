package com.we.di

import com.data.api.CoupleApi
import com.data.api.SignApi
import com.we.core.util.Qualifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideSignApi(
        @Qualifier.NoInterceptorRetrofit
        retrofit: Retrofit
    ): SignApi = retrofit.create()


    @Singleton
    @Provides
    fun provideCoupleApi(
        @Qualifier.InterceptorRetrofit
        retrofit: Retrofit
    ): CoupleApi = retrofit.create()

}