package com.qmk.control_center.vsm

import android.content.Context
import com.android.volley.Response
import com.qmk.control_center.network.VSMAPI
import org.json.JSONObject

class VirtualSoundMixer(private val context: Context) {
    private var api: VSMAPI = VSMAPI(context, "http://192.168.0.50:8080")
    private lateinit var cards: JSONObject

    fun updateCards(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        api.getCards(responseListener, responseErrorListener)
    }

    fun getCards() : JSONObject {
        return cards
    }
}