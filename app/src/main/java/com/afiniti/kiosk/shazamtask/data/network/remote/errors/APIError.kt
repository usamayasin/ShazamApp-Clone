package com.afiniti.kiosk.shazamtask.data.network.remote.errors

import com.afiniti.kiosk.shazamtask.base.ErrorParser
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response

open class APIError(
    var code: Int = -1,
    @SerializedName("error") var error: String? = null,
) : ErrorParser {

    override fun fromResponse(response: Response<*>): APIError? {
        return response.errorBody()?.let {
            try {
                val apiError = Gson().fromJson(it.string(), APIError::class.java) ?: return null
                apiError.code = response.code()
                apiError
            } catch (e: Exception) {
                null
            }
        }
    }
}