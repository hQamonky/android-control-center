package com.qmk.irremotepi

import android.content.Context
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.util.Log
import com.android.volley.Response
import org.json.JSONArray
import org.json.JSONObject

class IRRemotePi(private val context: Context) {
    private var api = API(context, buildUrlFromSettings())
    var devices: MutableSet<Device> = mutableSetOf()
    private lateinit var json: JSONArray
    private var listener: Listener? = null

    init {
        refresh()
    }

    fun refresh() {
        api.getDevices(
            Response.Listener { response ->
            println(response.toString())
            json = com.qmk.httpclient.getDataArray(response.toString())
            for (i in 0 until json.length()) {
                val device = json.getJSONObject(i)
                devices = mutableSetOf()
                devices.add(Device(device.getInt("id"), api))
            }
            listener?.onRefreshSuccess()
        }, Response.ErrorListener { error ->
            // Handle error
            println(error.toString())
            listener?.onRefreshFail()
        })
    }

    fun addDevice(name: String, gpio: Int) {
        val body = JSONObject("{ \"name\": $name, \"gpio\": $gpio }")
        api.createDevice(body,
            Response.Listener { response ->
                println(response.toString())
                val json = com.qmk.httpclient.getDataObject(response.toString())
                devices.add(Device(json.getInt("id"), api))
                listener?.onAddDeviceSuccess()
            }, Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
                listener?.onAddDeviceFail()
            })
    }

    fun deleteDevice(id: Int) {
        findDevice(id)?.delete()
        // TODO: Remove device form devices in success callback
        devices.remove(findDevice(id))
    }

    private fun findDevice(id: Int): Device? {
        for (device in devices) {
            if (device.id == id) {
                return device
            }
        }
        return  null
    }

    private fun buildUrlFromSettings() : String {
        val sharedPref = getDefaultSharedPreferences(context)

        val ip = sharedPref.getString("irpi_host_ip", "0.0.0.0")
        val port = sharedPref.getString("irpi_port", "8094")

        val url = if (port.equals(""))
            "http://$ip"
        else
            "http://$ip:$port"
        Log.d("" +
                "IRRemotePi", "Built url : $url.")

        return url
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onRefreshSuccess()
        fun onRefreshFail()
        fun onAddDeviceSuccess()
        fun onAddDeviceFail()
        fun onDeleteDeviceSuccess()
        fun onDeleteDeviceFail()
    }
}