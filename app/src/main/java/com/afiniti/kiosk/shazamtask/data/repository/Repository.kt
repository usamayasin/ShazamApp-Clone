package com.afiniti.kiosk.shazamtask.data.repository

import com.afiniti.kiosk.shazamtask.data.network.remote.ApiService
import com.afiniti.kiosk.shazamtask.model.Chart
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class Repository @Inject constructor(private val retrofit: Retrofit) {

    private var apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getTracks(): Observable<Response<Chart>> {
        return apiService.getTracks()
    }

}