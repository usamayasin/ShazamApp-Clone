package com.afiniti.kiosk.shazamtask.data.network.remote

import com.afiniti.kiosk.shazamtask.model.Chart
import com.afiniti.kiosk.shazamtask.utils.AppConstants
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(AppConstants.Network.CHART_ENDPOINT)
    fun getTracks(): Observable<Response<Chart>>

}