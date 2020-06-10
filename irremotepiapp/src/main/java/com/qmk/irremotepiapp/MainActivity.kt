package com.qmk.irremotepiapp

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.qmk.irremotepiapp.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var tabs: TabLayout
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
//        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener {
            addDeviceDialog()
        }

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    private fun addDeviceDialog() {
        val alertDialog: AlertDialog? = this.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout
            val linearLayout = layoutInflater.inflate(R.layout.dialog_new_device, findViewById(android.R.id.content), false)
            builder.apply {
                setTitle(R.string.new_device_dialog_title)
                setView(linearLayout)
                setPositiveButton(R.string.ok) { _, _ ->
                    // User clicked OK button
                    val textView: TextView = linearLayout.findViewById(R.id.device_name)
                    addDevice(textView.text.toString())
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

    private fun addDevice(name: String) {
        Log.d("MainActivity", "New device: $name")
        // Create new device
        // Create new tab
        tabs.addTab(tabs.newTab().setText(name))
        sectionsPagerAdapter.addTab(name)
    }

    private fun deleteDevice(id: Int) {

    }
}