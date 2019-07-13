package hr.ferit.igorkuridza.taskie.prefs

interface SharedPrefsHelper {

    fun getUserToken(): String

    fun storeUserToken(token: String)

    fun clearUserToken()
}