package com.afiniti.kiosk.shazamtask.data.network.remote.util

import android.app.Activity
import android.view.LayoutInflater
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.afiniti.kiosk.shazamtask.R
import com.afiniti.kiosk.shazamtask.data.network.remote.errors.APIError
import retrofit2.Response
import java.net.ConnectException

class ApiErrorHandler(activity: Activity) {
    private val inflater: LayoutInflater
    private val ctx: Activity = activity

    init {
        inflater = ctx.layoutInflater
    }

    fun handleError(apiResponse: ApiResponse<*>) {
        return when (apiResponse.errorType) {
            ApiResponse.ErrorType.THROWABLE -> handleException(
                apiResponse.exception as Exception
            )
            ApiResponse.ErrorType.CUSTOM -> handleCustomError(apiResponse.apiError!!)
            ApiResponse.ErrorType.RAW -> handleRawResponse(apiResponse.response)
            else -> Toast.makeText(ctx, "Unknown Error!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleException(exception: Exception) {
        when (exception) {
            is ConnectException -> {
                showInternetAlertDialog(ctx.getString(R.string.error_no_internet))
            }
            else -> {
                showErrorDialog(exception.message)
            }
        }
    }

    private fun handleCustomError(apiError: APIError) {
        showErrorDialog(apiError.error)
    }

    private fun handleRawResponse(response: Response<*>?) {
        response?.run {
            val apiError = APIError().fromResponse(this)
            apiError?.let {
                handleCustomError(it)
            } ?: run {
                showErrorDialog(response.message())
            }
        }
    }

    private fun showInternetAlertDialog(title: String) {
        if (ctx.isDestroyed || ctx.isFinishing) return
        Toast.makeText(ctx, title, LENGTH_LONG).show()
    }

    private fun showErrorDialog(msg: String?) {
        if (ctx.isDestroyed || ctx.isFinishing) return
        Toast.makeText(ctx, msg, LENGTH_LONG).show()
    }
}