package com.qmk.irremotepi

import com.qmk.httpclient.API

import android.content.Context
import com.android.volley.Response
import org.json.JSONObject

class API(context: Context, baseURL: String) : API(context, baseURL) {
    fun clearDatabase(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/database/clear", responseListener, responseErrorListener)
    }

    // Devices
    fun getDevices(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/devices", responseListener, responseErrorListener)
    }

    fun createDevice(arguments: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonPostRequest("/devices", arguments, responseListener, responseErrorListener)
    }

    fun getDevice(deviceId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/device/$deviceId", responseListener, responseErrorListener)
    }

    fun editDevice(deviceId: Int, arguments: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonPostRequest("/devices/$deviceId", arguments, responseListener, responseErrorListener)
    }

    fun deleteDevice(deviceId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonDeleteRequest("/device/$deviceId", responseListener, responseErrorListener)
    }

    // Commands
    fun recordCommand(deviceId: Int, arguments: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonPostRequest("/devices/$deviceId/record", arguments, responseListener, responseErrorListener)
    }

    fun editCommand(deviceId: Int, commandId: Int, arguments: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonPostRequest("/devices/$deviceId/command/$commandId", arguments, responseListener, responseErrorListener)
    }

    fun deleteCommand(deviceId: Int, commandId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonDeleteRequest("/device/$deviceId/command/$commandId", responseListener, responseErrorListener)
    }

    fun sendIRSignal(deviceId: Int, commandId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/device/$deviceId/send/$commandId", responseListener, responseErrorListener)
    }
}