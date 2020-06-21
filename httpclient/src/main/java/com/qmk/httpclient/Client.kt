package com.qmk.httpclient

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.concurrent.TimeUnit


abstract class Client(context: Context, private var baseURL: String) {

    private val volleyQueue = Volley.newRequestQueue(context)
    private val requestQueue: MutableList<JsonObjectRequest> = mutableListOf()
    private var isRunning = false
    private val requestTag = "syncRequest"

    fun setBaseUrl(url: String) {
        baseURL = url
    }

    private fun addRequest(request: JsonObjectRequest) {
        request.tag = requestTag
        requestQueue.add(request)
        if (!isRunning) {
            volleyQueue.add(requestQueue[0])
            isRunning = true
        }
    }

    private fun runNextRequest() {
        Log.d(requestTag, "Run next request...")
        requestQueue.removeAt(0)
        if (requestQueue.size > 0) {
            volleyQueue.add(requestQueue[0])
        } else {
            isRunning = false
        }
    }

    fun cancelSyncRequests() {
        volleyQueue.cancelAll(requestTag)
        requestQueue.clear()
        isRunning = false
    }

    fun syncJsonGetRequest(endpoint: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(Request.Method.GET, baseURL + endpoint, null, Response.Listener {
            responseListener.onResponse(it)
            runNextRequest()
        }, Response.ErrorListener {
            cancelSyncRequests()
            responseErrorListener.onErrorResponse(it)
        })
        // Add the request to the RequestQueue.
        addRequest(jsonRequest)
    }

    fun syncJsonPostRequest(endpoint: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(Request.Method.POST, baseURL + endpoint, body, Response.Listener {
            responseListener.onResponse(it)
            runNextRequest()
        }, Response.ErrorListener {
            cancelSyncRequests()
            responseErrorListener.onErrorResponse(it)
        })
        // Add the request to the RequestQueue.
        addRequest(jsonRequest)
    }

    fun syncJsonDeleteRequest(endpoint: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(Request.Method.DELETE, baseURL + endpoint, null, Response.Listener {
            responseListener.onResponse(it)
            runNextRequest()
        }, Response.ErrorListener {
            cancelSyncRequests()
            responseErrorListener.onErrorResponse(it)
        })
        // Add the request to the RequestQueue.
        addRequest(jsonRequest)
    }

    fun jsonGetRequest(endpoint: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(Request.Method.GET, baseURL + endpoint, null, responseListener, responseErrorListener)
        jsonRequest.retryPolicy = DefaultRetryPolicy(
            TimeUnit.SECONDS.toMillis(2500).toInt(),
            10,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        // Add the request to the RequestQueue.
        volleyQueue.add(jsonRequest)
    }

    fun jsonPostRequest(endpoint: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(Request.Method.POST, baseURL + endpoint, body, responseListener, responseErrorListener)
        jsonRequest.retryPolicy = DefaultRetryPolicy(
            TimeUnit.SECONDS.toMillis(2500).toInt(),
            5,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        // Add the request to the RequestQueue.
        volleyQueue.add(jsonRequest)
    }

    fun jsonDeleteRequest(endpoint: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(Request.Method.DELETE, baseURL + endpoint, null, responseListener, responseErrorListener)
        jsonRequest.retryPolicy = DefaultRetryPolicy(
            TimeUnit.SECONDS.toMillis(2500).toInt(),
            5,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        // Add the request to the RequestQueue.
        volleyQueue.add(jsonRequest)
    }
}