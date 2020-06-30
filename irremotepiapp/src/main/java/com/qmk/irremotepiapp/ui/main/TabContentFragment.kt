package com.qmk.irremotepiapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qmk.irremotepiapp.R

class TabContentFragment : Fragment() {

    companion object {
        fun newInstance() = TabContentFragment()
    }

    private lateinit var viewModel: TabContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_content_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TabContentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}