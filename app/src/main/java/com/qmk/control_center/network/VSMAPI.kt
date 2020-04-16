package com.qmk.control_center.network

import android.content.Context
import com.android.volley.Response
import org.json.JSONObject

class VSMAPI(context: Context, baseURL: String) : HttpClient(context, baseURL) {

    // Basic Info
    fun getStats(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/native/stats", responseListener, responseErrorListener)
    }

    fun getInfo(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/native/info", responseListener, responseErrorListener)
    }

    // Cards
    fun getCards(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/cards", responseListener, responseErrorListener)
    }

    fun setCardProfile(cardId: Int, profile: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/card/$cardId/set-profile/$profile", responseListener, responseErrorListener)
    }

    // Modules
    fun loadModule(module: String, arguments: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonPostRequest("/load-module/$module", arguments, responseListener, responseErrorListener)
    }

    fun unloadModule(moduleId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/unload-module/$moduleId", responseListener, responseErrorListener)
    }

    // Sinks
    fun getSinks(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/sinks", responseListener, responseErrorListener)
    }

    fun setDefaultSink(sinkId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/sink/$sinkId/set-default", responseListener, responseErrorListener)
    }

    fun setSinkPort(sinkId: Int, port: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/sink/$sinkId/set-port/$port", responseListener, responseErrorListener)
    }

    fun setSinkVolume(sinkId: Int, volume: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/sink/$sinkId/set-volume-percentage/$volume", responseListener, responseErrorListener)
    }

    fun setSinkMute(sinkId: Int, mute: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/sink/$sinkId/set-mute/$mute", responseListener, responseErrorListener)
    }

    // Sink inputs
    fun getSinkInputs(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/sink-inputs", responseListener, responseErrorListener)
    }

    fun setSinkInputVolume(sinkInputId: Int, volume: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/sink-input/$sinkInputId/set-volume-percentage/$volume", responseListener, responseErrorListener)
    }

    fun setSinkInputMute(sinkInputId: Int, mute: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/sink-input/$sinkInputId/set-mute/$mute", responseListener, responseErrorListener)
    }

    // Sources
    fun getSources(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/sources", responseListener, responseErrorListener)
    }

    fun setDefaultSource(sourceId: Int, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/source/$sourceId/set-default", responseListener, responseErrorListener)
    }

    fun setSourcePort(sourceId: Int, port: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/source/$sourceId/set-port/$port", responseListener, responseErrorListener)
    }

    fun setSourceVolume(sourceId: Int, volume: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/source/$sourceId/set-volume-percentage/$volume", responseListener, responseErrorListener)
    }

    fun setSourceMute(sourceId: Int, mute: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/source/$sourceId/set-mute/$mute", responseListener, responseErrorListener)
    }

    // Source outputs
    fun getSourceOutputs(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/source-outputs", responseListener, responseErrorListener)
    }

    fun setSourceOutputVolume(sourceOutputId: Int, volume: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/source-output/$sourceOutputId/set-volume-percentage/$volume", responseListener, responseErrorListener)
    }

    fun setSourceOutputMute(sourceOutputId: Int, mute: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/source-output/$sourceOutputId/set-mute/$mute", responseListener, responseErrorListener)
    }

    // Clients
    fun getClients(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        jsonGetRequest("/clients", responseListener, responseErrorListener)
    }
}