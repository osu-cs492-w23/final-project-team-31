package com.example.main.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.main.R

class SettingsFragment : PreferenceFragmentCompat() {
    private val playerStatsViewModel: PlayerStatsViewModel by viewModels()
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val button = preferenceManager.findPreference<Preference>(getString(R.string.pref_reset_key))
        button?.setOnPreferenceClickListener { Preference ->
            Log.d("Settings", "Reset statistics setting clicked")
            playerStatsViewModel.wipeDatabase()
            true
        }
    }
}