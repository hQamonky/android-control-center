package com.qmk.irremotepi

import com.android.volley.Response
import org.json.JSONObject

class Command(private val deviceId: Int, private val id: Int, private var name: String, private val api: API) {
    private var listener: Listener? = null

    fun send() {
        api.sendIRSignal(deviceId, id,
            Response.Listener { response ->
                println(response.toString())
                listener?.onSendSuccess()
            },
            Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
                listener?.onSendFail()
            })
    }

    fun setName(name: String) {
        val body = JSONObject("{ \"command_name\": $name }")
        api.editCommand(deviceId, id, body,
            Response.Listener { response ->
                println(response.toString())
                this.name = name
                listener?.onSetNameSuccess()
            },
            Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
                listener?.onSetNameFail()
            })
    }

    fun delete() {
        api.deleteCommand(deviceId, id,
            Response.Listener { response ->
                println(response.toString())
                listener?.onDeleteSuccess()
            },
            Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
                listener?.onDeleteFail()
            })
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onSendSuccess()
        fun onSendFail()
        fun onSetNameSuccess()
        fun onSetNameFail()
        fun onDeleteSuccess()
        fun onDeleteFail()
    }
}