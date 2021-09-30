package com.afiniti.kiosk.shazamtask.utils.stringUtil

import android.content.Context
import javax.inject.Inject

class StringProviderImpl @Inject constructor(private val context: Context) :
    StringProvider {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }
}