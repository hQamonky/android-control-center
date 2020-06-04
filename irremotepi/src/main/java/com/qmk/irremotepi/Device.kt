package com.qmk.irremotepi

import com.android.volley.Response
import org.json.JSONObject

class Device(val id: Int, private val api: API) {
    private var name: String? = null
//        get() = this.name
    private var gpio: Int? = null
//        get() = this.gpio
    var commands: MutableList<Command> = mutableListOf()
    private lateinit var json: JSONObject
    private var listener: Listener? = null

    init {
        // Initialize members
        refresh()
    }

    fun refresh() {
        api.getDevice(id,
            Response.Listener { response ->
                println(response.toString())
                json = JSONObject(response.toString()).getJSONObject("data")
                name = json.getString("name")
                gpio = json.getInt("gpio")
                for (i in 0 until json.getJSONArray("commands").length()) {
                    val command = json.getJSONArray("commands").getJSONObject(i)
                    commands = mutableListOf()
                    commands.add(Command(id, command.getInt("id"), command.getString("name"), api))
                }
                listener?.onRefreshSuccess()
            }, Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
                listener?.onRefreshFail()
            })
    }

    fun record(commandName: String) {
        val body = JSONObject("{ \"command_name\": $commandName }")
        api.recordCommand(id, body,
            Response.Listener { response ->
                println(response.toString())
                // Add new command to commands member
                val command = JSONObject(response.toString())
                commands.add(Command(id, command.getInt("id"), commandName, api))
                listener?.onRecordSuccess()
            },
            Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
                listener?.onRecordFail()
            })
    }

    fun deleteCommand(id: Int) {
        commands[id].delete()
        // TODO: Remove command form commands in success callback
        commands.removeAt(id)
    }

    fun delete() {
        api.deleteDevice(id,
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

    fun setName(name: String) {
        val body = JSONObject("{ \"name\": $name, \"gpio\": $gpio }")
        api.editDevice(id, body,
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

    fun setGpio(gpio: Int) {
        val body = JSONObject("{ \"name\": $name, \"gpio\": $gpio }")
        api.editDevice(id, body,
            Response.Listener { response ->
                println(response.toString())
                this.gpio = gpio
                listener?.onSetGpioSuccess()
            },
            Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
                listener?.onSetGpioFail()
            })
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun onRefreshSuccess()
        fun onRefreshFail()
        fun onRecordSuccess()
        fun onRecordFail()
        fun onDeleteCommandSuccess()
        fun onDeleteCommandFail()
        fun onSetNameSuccess()
        fun onSetNameFail()
        fun onSetGpioSuccess()
        fun onSetGpioFail()
        fun onDeleteSuccess()
        fun onDeleteFail()
    }
}