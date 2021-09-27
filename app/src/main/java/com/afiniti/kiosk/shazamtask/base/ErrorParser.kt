package com.afiniti.kiosk.shazamtask.base

import retrofit2.Response

interface ErrorParser {
    fun fromResponse(response: Response<*>): Any?
}