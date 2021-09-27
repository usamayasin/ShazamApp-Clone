package com.afiniti.kiosk.shazamtask.data.network.remote.util

import android.view.View
import com.afiniti.kiosk.shazamtask.data.network.remote.errors.APIError
import retrofit2.Response

class ApiResponse<T: Any> private constructor(var status: Status, var reqCode: Int = -1) {
    enum class Status {
        IN_PROCESS, ERROR, SUCCESS
    }

    enum class ErrorType {
        THROWABLE, RAW, CUSTOM
    }

    // In case of error
    var exception: Throwable? = null
    var response: Response<*>? = null
    var apiError: APIError? = null
    var errorType: ErrorType? = null

    // In case of success
    lateinit var data: T

    private constructor(data: T, reqCode: Int) : this(Status.SUCCESS, reqCode) {
        this.data = data
        View.VISIBLE
    }

    private constructor(errorType: ErrorType, reqCode: Int) : this(Status.ERROR, reqCode) {
        this.errorType = errorType
    }

    private constructor(exception: Throwable, reqCode: Int) : this(ErrorType.THROWABLE, reqCode) {
        this.exception = exception
    }

    private constructor(response: Response<*>, reqCode: Int) : this(ErrorType.RAW, reqCode) {
        this.response = response
    }

    private constructor(apiError: APIError, reqCode: Int) : this(ErrorType.CUSTOM, reqCode) {
        this.apiError = apiError
    }

    companion object {
        fun <T : Any> loading(reqCode: Int = -1): ApiResponse<T> {
            return ApiResponse(Status.IN_PROCESS, reqCode)
        }

        fun <T: Any> success(reqCode: Int = -1): ApiResponse<T> {
            return ApiResponse(Status.SUCCESS, reqCode)
        }

        fun <T: Any> success(data: T, reqCode: Int = -1): ApiResponse<T> {
            return ApiResponse(data, reqCode)
        }

        fun <T: Any> error(error: Throwable, reqCode: Int = -1): ApiResponse<T> {
            return ApiResponse(error, reqCode)
        }

        fun <T: Any> error(error: Response<*>, reqCode: Int = -1): ApiResponse<T> {
            return ApiResponse(error, reqCode)
        }

        fun <T: Any> error(error: APIError, reqCode: Int = -1): ApiResponse<T> {
            return ApiResponse(error, reqCode)
        }
    }
}