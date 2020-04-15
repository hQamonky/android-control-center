package com.qmk.control_center.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

open class HttpClient(private val context: Context, private val baseURL: String) {

    fun jsonGetRequest(endpoint: String, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        // Request a string response from the provided URL.
        val stringRequest = JsonObjectRequest(Request.Method.GET, baseURL + endpoint, null, responseListener, responseErrorListener)
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun jsonPostRequest(endpoint: String, body: JSONObject, responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        // Request a string response from the provided URL.
        val jsonRequest = JsonObjectRequest(Request.Method.POST, baseURL + endpoint, body, responseListener, responseErrorListener)
        // Add the request to the RequestQueue.
        queue.add(jsonRequest)
    }
}