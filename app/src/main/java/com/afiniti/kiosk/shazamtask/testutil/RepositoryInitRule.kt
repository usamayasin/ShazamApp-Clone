package com.afiniti.kiosk.shazamtask.testutil

import com.afiniti.kiosk.shazamtask.data.network.remote.ApiService
import com.afiniti.kiosk.shazamtask.data.repository.Repository
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RepositoryInitRule(private val mockServerRule: MockServerRule) : TestWatcher() {
    lateinit var repository: Repository

    override fun starting(description: Description) {
        super.starting(description)
        val okHttp = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttp)
            .baseUrl(mockServerRule.serverUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiInterface: ApiService = retrofit.create(ApiService::class.java)
        repository = Repository(apiInterface)
    }
}