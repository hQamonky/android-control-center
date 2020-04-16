package com.qmk.control_center.ui.vsm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import com.android.volley.Response
import com.qmk.control_center.R
import com.qmk.control_center.vsm.VirtualSoundMixer
import kotlinx.android.synthetic.main.nav_header_main.*

class VSMFragment : Fragment() {

    private lateinit var vsm : VirtualSoundMixer
    private lateinit var refreshButton: ImageButton
    private lateinit var textView: TextView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_vsm, container, false)
        refreshButton = root.findViewById(R.id.refresh_button)
        textView = root.findViewById(R.id.text_vsm)

        vsm = context?.let { VirtualSoundMixer(it) }!!
        refreshButton.setOnClickListener {
            refresh()
        }

        refresh()
        return root
    }

    private fun refresh() {
        vsm.updateCards(
            Response.Listener { response ->
                println(response.toString())
                textView.text = response.toString()
            },
            Response.ErrorListener { error ->
                // Handle error
                println(error.toString())
                textView.text = error.message
            })
    }
}
