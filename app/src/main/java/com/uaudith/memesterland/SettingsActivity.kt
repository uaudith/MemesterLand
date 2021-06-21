package com.uaudith.memesterland

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat


class SettingsActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MemesterLand)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settingsFrame, SettingsFragment())
                .commit()
        }
        toolbar = findViewById(R.id.settingsToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private val tagMsg = "SettingsActivity"
        private val darkModeListener = Preference.OnPreferenceChangeListener { _, newValue ->
            Log.i(tagMsg,"new value for darkmode $newValue and ${newValue::class}")

            when (newValue as Boolean){
                true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            val infoPref: Preference? = findPreference("info")
            val darkMode : SwitchPreferenceCompat? = findPreference("darkMode")

            infoPref?.summary = BuildConfig.APPLICATION_ID+" "+BuildConfig.VERSION_NAME
            darkMode?.onPreferenceChangeListener = darkModeListener


        }

        }
}