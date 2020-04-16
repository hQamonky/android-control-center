package com.qmk.control_center.vsm

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.android.volley.Response
import com.qmk.control_center.network.VSMAPI
import org.json.JSONObject

class VirtualSoundMixer(private val context: Context) {
    private var api: VSMAPI = VSMAPI(context, buildUrlFromSettings())
    private lateinit var cards: JSONObject

    fun updateCards(responseListener: Response.Listener<JSONObject>, responseErrorListener: Response.ErrorListener) {
        api.getCards(responseListener, responseErrorListener)
    }

    fun getCards() : JSONObject {
        return cards
    }

    private fun buildUrlFromSettings() : String {
        val sharedPref = getDefaultSharedPreferences(context)

        val ip = sharedPref.getString("vsm_host_ip", "0.0.0.0")
        val port = sharedPref.getString("vsm_port", "")

        val url = if (port.equals(""))
            "http://$ip"
        else
            "http://$ip:$port"
        Log.d("VirtualSoundMixer", "Built url : $url.")

        return url
    }
}