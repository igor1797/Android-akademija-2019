package hr.ferit.igorkuridza.taskiefragments.persistence

import android.preference.PreferenceManager
import hr.ferit.igorkuridza.taskiefragments.app.Taskie


object TaskPrefs {

    const val PREFERENCE_PRIORITY = "Priority"

    private fun sharedPrefs()=
        PreferenceManager.getDefaultSharedPreferences(Taskie.getAppContext())

    fun store(key: String, value: Int){
        val editor = sharedPrefs().edit()
        editor.putInt(key,value).apply()
    }

    fun getPriority(key: String, defaultValue: Int) = sharedPrefs().getInt(key,defaultValue)
}