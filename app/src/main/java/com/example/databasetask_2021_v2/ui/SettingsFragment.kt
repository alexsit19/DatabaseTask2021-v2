package com.example.databasetask_2021_v2.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.databasetask_2021_v2.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        val listPref = findPreference<ListPreference>("list")
        Log.d("DEBUG", "Prefs: ${listPref?.value}")
    }
}
