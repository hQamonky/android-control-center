package com.qmk.control_center.ui.vsm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.qmk.control_center.R

class VSMFragment : Fragment() {

    private lateinit var vsmViewModel: VSMViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        vsmViewModel =
                ViewModelProviders.of(this).get(VSMViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_vsm, container, false)
        val textView: TextView = root.findViewById(R.id.text_vsm)
        vsmViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
