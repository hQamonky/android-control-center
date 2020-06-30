package com.qmk.irremotepiapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.qmk.irremotepi.Device
import com.qmk.irremotepiapp.MainActivity
import com.qmk.irremotepiapp.R

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        pageViewModel.text.observe(viewLifecycleOwner, Observer<String> {
            textView.text = it
        })
        val addButton: Button = root.findViewById(R.id.add_button)
        addButton.setOnClickListener {
            addCommandDialog()
        }
        return root
    }

    private fun addCommandDialog() {
        val alertDialog: AlertDialog? = this.let {
            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            // Get the layout
            val view = View.inflate(context,R.layout.dialog_new_command,null)
            val recordingDialog = recordingDialog()
            builder?.apply {
                setTitle(R.string.new_command_dialog_title)
                setView(view)
                setPositiveButton(R.string.ok) { dialog, _ ->
                    // User clicked OK button
                    val nameTextView: TextView = view.findViewById(R.id.command_name)
                    pageViewModel.getIndex()?.let { it1 ->
                        startRecording(nameTextView.text.toString(),
                            it1-1, object : Device.Listener() {
                                override fun onRecordSuccess() {
                                    super.onRecordSuccess()
                                    println("Command recorded.")
                                    recordingDialog!!.dismiss()
                                }
                            }
                        )
                        dialog.dismiss()
                        recordingDialog!!.show()
                    }
                }
                setNegativeButton(R.string.cancel) { dialog, _ ->
                    // User cancelled the dialog
                    dialog.dismiss()
                }
            }
            // Create the AlertDialog
            builder?.create()
        }
        alertDialog!!.show()
    }

    private fun startRecording(commandName: String, deviceId: Int, listener: Device.Listener? = null) {
        val device = MainActivity.SingleIRRemotePi.instance.devices[deviceId]
        Log.d("PlaceholderFragment", "New command for: ${device.getName()}")
        // Create new device
        device.record(commandName, listener)
    }

    private fun recordingDialog(): AlertDialog? {
        return let {
            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            // Get the layout
            val view = View.inflate(context,R.layout.dialog_new_command,null)
            builder?.apply {
                setTitle(R.string.recording_command_dialog_title)
                setMessage(R.string.recording_command_dialog_message)
//                setNegativeButton(R.string.cancel) { dialog, _ ->
//                    // User cancelled the dialog
//                    // Cancel command recording
//                    MainActivity.SingleIRRemotePi.instance.cancelRecording()
//                    dialog.dismiss()
//                }
            }
            // Create the AlertDialog
            builder?.create()
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}