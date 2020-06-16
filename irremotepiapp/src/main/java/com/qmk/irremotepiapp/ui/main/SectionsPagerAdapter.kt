package com.qmk.irremotepiapp.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.qmk.irremotepiapp.MainActivity

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    var tabTitles: MutableList<String> = mutableListOf()
    var irRemotePi = MainActivity.SingleIRRemotePi.instance

    init {
        for (device in irRemotePi.devices) {
            tabTitles.add(device.getName())
        }
    }

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

    override fun getCount(): Int {
        return tabTitles.size
    }

    fun addTab(title: String) {
        tabTitles.add(title)
        notifyDataSetChanged()
    }

    fun removeTab(position: Int) {
        if (tabTitles.isNotEmpty() && position < tabTitles.size) {
            tabTitles.removeAt(position)
            notifyDataSetChanged()
        }
    }
}