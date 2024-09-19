package com.we.di

import com.google.gson.GsonBuilder
import com.we.core.util.TokenInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //local property로 빼기
    val baseUrl = "http://192.168.100.149:8080/v1/"

    @com.we.core.util.Qualifier.InterceptorRetrofit
    @Singleton
    @Provides
    fun provideInterceptorRetrofit(@com.we.core.util.Qualifier.InterceptorOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }


    @com.we.core.util.Qualifier.NoInterceptorRetrofit
    @Singleton
    @Provides
    fun provideRetrofit(@com.we.core.util.Qualifier.NoInterceptorOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }


    @com.we.core.util.Qualifier.NoInterceptorOkHttpClient
    @Singleton
    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder().run {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        connectTimeout(120, TimeUnit.SECONDS)
        readTimeout(120, TimeUnit.SECONDS)
        writeTimeout(120, TimeUnit.SECONDS)
        build()
    }


    @com.we.core.util.Qualifier.InterceptorOkHttpClient
    @Singleton
    @Provides
    fun provideInterceptorOkHttpClient(interceptor: TokenInterceptor) = OkHttpClient.Builder().run {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        connectTimeout(120, TimeUnit.SECONDS)
        readTimeout(120, TimeUnit.SECONDS)
        writeTimeout(120, TimeUnit.SECONDS)
        addInterceptor(interceptor)
        build()
    }
}
