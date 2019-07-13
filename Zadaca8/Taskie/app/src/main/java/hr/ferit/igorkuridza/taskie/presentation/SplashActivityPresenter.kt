package hr.ferit.igorkuridza.taskie.presentation

import hr.ferit.igorkuridza.taskie.prefs.SharedPrefsHelper
import hr.ferit.igorkuridza.taskie.ui.activities.splash.SplashContract

class SplashActivityPresenter(private val prefs: SharedPrefsHelper): SplashContract.Presenter {

    private lateinit var view: SplashContract.View

    override fun setView(view: SplashContract.View) {
        this.view = view
    }

    override fun checkPrefs() {
        if(prefs.getUserToken().isEmpty()) view.onEmptyToken()
        else view.onNotEmptyToken()
    }
}