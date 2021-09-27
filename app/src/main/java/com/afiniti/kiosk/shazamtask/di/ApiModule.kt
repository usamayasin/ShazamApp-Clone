package com.afiniti.kiosk.shazamtask.di

import com.afiniti.kiosk.shazamtask.data.repository.Repository
import com.afiniti.kiosk.shazamtask.utils.AppConstants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule constructor(private val baseURL: String) {

    @Singleton
    @Provides
    fun provideOKHttpClient(): OkHttpClient {

        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .readTimeout(AppConstants.Network.TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(AppConstants.Network.TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideGSON(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideRepository(retrofit: Retrofit): Repository {
        return Repository(retrofit)
    }
}