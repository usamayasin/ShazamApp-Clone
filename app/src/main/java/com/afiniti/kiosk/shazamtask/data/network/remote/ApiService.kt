package com.afiniti.kiosk.shazamtask.data.network.remote

import com.afiniti.kiosk.shazamtask.model.Chart
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("tracks/web_chart_future_hits_us")
    fun getTracks(): Observable<Response<Chart>>

}