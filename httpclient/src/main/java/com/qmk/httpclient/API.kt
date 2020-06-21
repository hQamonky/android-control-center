package com.qmk.httpclient

import android.content.Context
import com.android.volley.Response
import org.json.JSONArray
import org.json.JSONObject

fun getDataObject(response: String): JSONObject {
    val json = JSONObject(response)
    return json.getJSONObject("data")
}

fun getDataArray(response: String): JSONArray {
    val json = JSONObject(response)
    return json.getJSONArray("data")
}

fun getResponseMessage(response: String): String {
    val json = JSONObject(response)
    return json.getString("message")
}

abstract class API(context: Context, baseURL: String) : Client(context, baseURL)  {
    fun getUserGuide(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonGetRequest("/", responseListener, responseErrorListener)
    }
}