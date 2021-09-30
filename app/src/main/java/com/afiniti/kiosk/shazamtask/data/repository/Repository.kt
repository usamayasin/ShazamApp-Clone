package com.afiniti.kiosk.shazamtask.data.repository

import com.afiniti.kiosk.shazamtask.data.network.remote.ApiService
import com.afiniti.kiosk.shazamtask.model.Chart
import io.reactivex.Observable
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    fun getTracks(): Observable<Response<Chart>> {
        return apiService.getTracks()
    }
}