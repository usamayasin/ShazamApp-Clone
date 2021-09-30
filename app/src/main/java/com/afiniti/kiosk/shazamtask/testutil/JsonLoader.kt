package com.afiniti.kiosk.shazamtask.testutil

import android.content.Context
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type
import java.nio.charset.Charset

object JsonLoader {
    @JvmStatic
    fun loadJSONFromAsset(context: Context, filename: String): JSONObject {
        val json: String? = try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            throw RuntimeException(ex.message)
        }
        return try {
            JSONObject(json!!)
        } catch (e: JSONException) {
            throw RuntimeException(e.message)
        }
    }

    @JvmStatic
    fun toString(jsonObj: JSONObject, key: String): String {
        return try {
            jsonObj[key].toString()
        } catch (e: JSONException) {
            throw RuntimeException(e.message)
        }
    }

    @JvmStatic
    fun getString(ctx: Context, jsonAssetFile: String, key: String): String {
        return try {
            val jsonObj = loadJSONFromAsset(ctx, jsonAssetFile)
            jsonObj[key].toString()
        } catch (e: JSONException) {
            throw RuntimeException(e.message)
        }
    }

    @JvmStatic
    fun <T> toClass(jsonObj: JSONObject, key: String, classOfT: Class<T>): T {
        return try {
            Gson().fromJson(toString(jsonObj, key), classOfT)
        } catch (e: JSONException) {
            throw RuntimeException(e.message)
        }
    }

    @JvmStatic
    fun <T> toClass(jsonObj: JSONObject, key: String, type: Type): T {
        return try {
            Gson().fromJson(toString(jsonObj, key), type)
        } catch (e: JSONException) {
            throw RuntimeException(e.message)
        }
    }

    @JvmStatic
    fun <T> getClass(ctx: Context, jsonAssetFile: String, key: String, classOfT: Class<T>): T {
        return try {
            val jsonObj = loadJSONFromAsset(ctx, jsonAssetFile)
            Gson().fromJson(toString(jsonObj, key), classOfT)
        } catch (e: JSONException) {
            throw RuntimeException(e.message)
        }
    }

    @JvmStatic
    fun <T> getClass(ctx: Context, jsonAssetFile: String, key: String,  type: Type): T {
        return try {
            val jsonObj = loadJSONFromAsset(ctx, jsonAssetFile)
            Gson().fromJson(toString(jsonObj, key), type)
        } catch (e: JSONException) {
            throw RuntimeException(e.message)
        }
    }
}