package hr.ferit.igorkuridza.taskie.persistence

import android.content.Context
import android.preference.PreferenceManager
import hr.ferit.igorkuridza.taskie.Taskie
import java.util.prefs.Preferences
import java.util.prefs.PreferencesFactory

object TaskPrefs {

    const val PREFERENCE_PRIORITY = "PriorityColor"

    private fun sharedPrefs() =
            PreferenceManager.getDefaultSharedPreferences(Taskie.getAppContext())

    fun store(key: String, value: String){
        val editor = sharedPrefs().edit()
        editor.putString(key,value).apply()
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPrefs().getString(key,defaultValue)
    }
}