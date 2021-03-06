package com.jakting.rn6pan.activity.common

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.jakting.rn6pan.BaseActivity
import com.jakting.rn6pan.R
import com.jakting.rn6pan.utils.MyApplication.Companion.settingSharedPreferencesEditor
import com.jakting.rn6pan.utils.toast


class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        if (savedInstanceState == null) {
            val fragment = SettingsFragment()
            supportFragmentManager.beginTransaction().replace(R.id.settings, fragment).commit()
        }

        setSupportActionBar(findViewById(R.id.toolbar))
        if (supportActionBar != null) {
            supportActionBar!!.title = getString(R.string.setting_toolbar_title)
            //supportActionBar!!.subtitle = "v" + BuildConfig.VERSION_NAME
        }
//        if (!getDarkModeStatus(this)) {
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    internal class SettingsFragment : PreferenceFragmentCompat(),
        OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {
        override fun onCreatePreferences(bundle: Bundle?, s: String?) {
            setPreferencesFromResource(R.xml.settings, null)

            val clearCachePreferences:Preference? =  findPreference("click_video_clear_cache")
            clearCachePreferences?.setOnPreferenceClickListener {
                false
            }

            val showGuidePreferences:Preference? = findPreference("click_misc_show_guide")
            showGuidePreferences?.setOnPreferenceClickListener {
                settingSharedPreferencesEditor.putBoolean("first_run", true)
                settingSharedPreferencesEditor.apply()
                toast(getString(R.string.setting_misc_show_guide_toast))
                true
            }
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)
        }

        override fun onResume() {
            super.onResume()
            preferenceScreen.sharedPreferences
                .registerOnSharedPreferenceChangeListener(this)
        }

        override fun onSharedPreferenceChanged(
            sharedPreferences: SharedPreferences,
            key: String
        ) {}

        override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
            return true
        }
    }


}