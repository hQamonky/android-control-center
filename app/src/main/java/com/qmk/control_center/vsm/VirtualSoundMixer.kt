package com.qmk.control_center.vsm

import android.content.Context
import com.android.volley.Response
import com.qmk.control_center.MainActivity
import com.qmk.control_center.network.VSMAPI
import org.json.JSONObject

class VirtualSoundMixer(private val context: Context) {
    private var api: VSMAPI = VSMAPI(context, "192.168.0.50")
    private lateinit var cards: JSONObject

    init {
        api.getCards(
            Response.Listener { response ->
            cards = response
        },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }

        )
    }

    fun getCards() : JSONObject {
        return cards
    }
}