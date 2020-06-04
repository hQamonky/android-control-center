package com.qmk.irremotepi

import android.content.Context
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import org.json.JSONArray
import org.json.JSONObject

class IRRemotePi(private val context: Context) {
    private var api = API(context, buildUrlFromSettings())
    var devices: MutableList<Device> = mutableListOf()
    private lateinit var json: JSONArray
    private var listener: Listener? = null

    init {
        // Initiate members
        refresh()
        // Set settings change listener
        val sharedPref = getDefaultSharedPreferences(context)
        sharedPref.registerOnSharedPreferenceChangeListener {
                _, key ->
            println(key)
            if (key == "host_ip" || key == "port")
                refreshUrl()
        }
    }

    fun refresh() {
        // Update members from API
        api.getDevices(
            Response.Listener { response ->
            println(response.toString())
            json = com.qmk.httpclient.getDataArray(response.toString())
            for (i in 0 until json.length()) {
                val device = json.getJSONObject(i)
                devices = mutableListOf()
                devices.add(Device(device.getInt("id"), api))
            }
            listener?.onRefreshSuccess()
        }, Response.ErrorListener { error ->
            // Handle error
            println(error.toString())
            listener?.onRefreshFail()
        })
    }

    fun factoryReset() {
        val alertDialog: AlertDialog? = context.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.factory_reset_dialog_title)
                setMessage(R.string.factory_reset_dialog_message)
                setPositiveButton(R.string.ok) { _, _ ->
                    // User clicked OK button
                    api.clearDatabase(
                        Response.Listener { response ->
                            println(response.toString())
                            devices.clear()
                            listener?.onFactoryResetSuccess()
                        }, Response.ErrorListener { error ->
                            // Handle error
                            println(error.toString())
                            listener?.onFactoryResetFail()
                        })
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    // User cancelled the dialog
                    dialog.dismiss()
                }
            }
            // Create the AlertDialog
            builder.create()
        }
        alertDialog!!.show()
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
        devices[id].delete()
        // TODO: Remove device form devices in success callback
        devices.removeAt(id)
    }

    private fun buildUrlFromSettings() : String {
        val sharedPref = getDefaultSharedPreferences(context)

        val ip = sharedPref.getString("host_ip", "0.0.0.0")
        val port = sharedPref.getString("port", "8094")

        val url = if (port.equals(""))
            "http://$ip"
        else
            "http://$ip:$port"
        Log.d("IRRemotePi", "Built url : $url.")

        return url
    }

    private fun refreshUrl() {
        api.setBaseUrl(buildUrlFromSettings())
    }

    fun setListener(listener: Listener) {
        // Advanced article on listeners: https://antonioleiva.com/listeners-several-functions-kotlin/
        this.listener = listener
    }

    interface Listener {
        fun onRefreshSuccess()
        fun onRefreshFail()
        fun onFactoryResetSuccess()
        fun onFactoryResetFail()
        fun onAddDeviceSuccess()
        fun onAddDeviceFail()
        fun onDeleteDeviceSuccess()
        fun onDeleteDeviceFail()
    }
}