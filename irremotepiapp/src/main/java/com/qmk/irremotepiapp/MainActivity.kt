package com.qmk.irremotepiapp

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.qmk.irremotepi.IRRemotePi
import com.qmk.irremotepiapp.ui.main.SectionsPagerAdapter
import com.qmk.irremotepiapp.ui.main.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tabs: TabLayout
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        SingleIRRemotePi.instance = IRRemotePi(this)

        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        tabs = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener {
            addDeviceDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_settings -> {
                Log.d("MainMenu", "Selected Settings, start activity.")
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_delete_device -> {
                Log.d("MainMenu", "Selected Delete Device, show dialog.")
                deleteDeviceDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    private fun deleteDeviceDialog() {
        val array = sectionsPagerAdapter.tabTitles.toTypedArray()
        if (array.isNotEmpty()) {
            val alertDialog: AlertDialog? = this.let {
                val builder = AlertDialog.Builder(it)
                // Get the layout
                var selectedItem = 0
                builder.apply {
                    setTitle(R.string.new_device_dialog_title)
                    setSingleChoiceItems(array, selectedItem) { _: DialogInterface, i: Int ->
                        selectedItem = i
                    }
                    setPositiveButton(R.string.delete) { _, _ ->
                        // User clicked Delete button
                        deleteDevice(selectedItem)
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
    }

    private fun deleteDevice(position: Int) {
        Log.d("MainActivity", "Delete device: $position")
        if (tabs.tabCount >= 1 && position < tabs.tabCount) {
            tabs.removeTabAt(position)
            sectionsPagerAdapter.removeTab(position)
        }
    }

    object SingleIRRemotePi {

        init {
            println("Singleton class invoked.")
        }
        lateinit var instance: IRRemotePi
    }
}