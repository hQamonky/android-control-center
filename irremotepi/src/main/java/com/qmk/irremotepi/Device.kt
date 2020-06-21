package com.qmk.irremotepi

import android.util.Log
import com.android.volley.Response
import org.json.JSONObject

class Device(val id: Int, var listener: Listener) {
    private var name: String? = null
    private var gpio: Int? = null
//        get() = this.gpio
    var commands: MutableList<Command> = mutableListOf()
    private lateinit var json: JSONObject

    init {
        // Initialize members
        refresh()
    }

    fun refresh() {
        IRRemotePi.SingleApi.instance.getDevice(id,
            Response.Listener { response ->
                println(response.toString())
                json = JSONObject(response.toString()).getJSONObject("data")
                name = json.getString("name")
                gpio = json.getInt("gpio")
                commands = mutableListOf()
                for (i in 0 until json.getJSONArray("commands").length()) {
                    val command = json.getJSONArray("commands").getJSONObject(i)
                    Log.d("Device", "Adding Command: ${command.getString("name")}")
                    commands.add(Command(id, command.getInt("id"), command.getString("name")))
                }
                listener.onRefreshDeviceSuccess(this)
            }, Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
                listener.onRefreshDeviceFail(this)
            })
    }

    fun record(commandName: String) {
        val body = JSONObject("{ \"command_name\": $commandName }")
        IRRemotePi.SingleApi.instance.recordCommand(id, body,
            Response.Listener { response ->
                println(response.toString())
                // Add new command to commands member
                val command = JSONObject(response.toString())
                commands.add(Command(id, command.getInt("id"), commandName))
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
        IRRemotePi.SingleApi.instance.deleteDevice(id,
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

    fun getName(): String {
        return name.toString()
    }

    fun setName(name: String) {
        val body = JSONObject("{ \"name\": $name, \"gpio\": $gpio }")
        IRRemotePi.SingleApi.instance.editDevice(id, body,
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
        IRRemotePi.SingleApi.instance.editDevice(id, body,
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

    open class Listener {
        open fun onRefreshDeviceSuccess(device: Device) {

        }
        open fun onRefreshDeviceFail(device: Device) {

        }
        open fun onRecordSuccess() {

        }
        open fun onRecordFail() {

        }
        open fun onDeleteCommandSuccess() {

        }
        open fun onDeleteCommandFail() {

        }
        open fun onSetNameSuccess() {

        }
        open fun onSetNameFail() {

        }
        open fun onSetGpioSuccess() {

        }
        open fun onSetGpioFail() {

        }
        open fun onDeleteSuccess() {

        }
        open fun onDeleteFail() {

        }
    }
}