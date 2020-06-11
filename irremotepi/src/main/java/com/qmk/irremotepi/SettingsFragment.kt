package com.qmk.irremotepi

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment(private val listener: Preference.OnPreferenceClickListener): PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_screen, rootKey)
        val button: Preference? = findPreference("factory_reset")
        button?.onPreferenceClickListener = listener
    }
}