package com.qmk.irremotepi

import com.qmk.httpclient.API

import android.content.Context
import com.android.volley.Response
import org.json.JSONObject

class API(context: Context, baseURL: String) : API(context, baseURL) {
    fun clearDatabase(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonGetRequest("/database/clear", responseListener, responseErrorListener)
    }

    // Devices
    fun getDevices(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonGetRequest("/devices", responseListener, responseErrorListener)
    }

    fun createDevice(arguments: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonPostRequest("/devices", arguments, responseListener, responseErrorListener)
    }

    fun getDevice(deviceId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonGetRequest("/device/$deviceId", responseListener, responseErrorListener)
    }

    fun editDevice(deviceId: Int, arguments: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonPostRequest("/devices/$deviceId", arguments, responseListener, responseErrorListener)
    }

    fun deleteDevice(deviceId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonDeleteRequest("/device/$deviceId", responseListener, responseErrorListener)
    }

    // Commands
    fun recordCommand(deviceId: Int, arguments: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonPostRequest("/device/$deviceId/record", arguments, responseListener, responseErrorListener)
    }

    fun editCommand(deviceId: Int, commandId: Int, arguments: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonPostRequest("/devices/$deviceId/command/$commandId", arguments, responseListener, responseErrorListener)
    }

    fun deleteCommand(deviceId: Int, commandId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonDeleteRequest("/device/$deviceId/command/$commandId", responseListener, responseErrorListener)
    }

    fun sendIRSignal(deviceId: Int, commandId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        syncJsonGetRequest("/device/$deviceId/send/$commandId", responseListener, responseErrorListener)
    }
}