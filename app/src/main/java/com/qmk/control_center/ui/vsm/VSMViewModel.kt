package com.qmk.control_center.ui.vsm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VSMViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is virtual sound mixer Fragment"
    }
    val text: LiveData<String> = _text
}