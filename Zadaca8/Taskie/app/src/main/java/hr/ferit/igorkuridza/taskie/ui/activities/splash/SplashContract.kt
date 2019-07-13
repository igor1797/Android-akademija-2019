package hr.ferit.igorkuridza.taskie.ui.activities.splash

import hr.ferit.igorkuridza.taskie.ui.activities.base.BasePresenter

interface SplashContract {

    interface View{

        fun onEmptyToken()

        fun onNotEmptyToken()
    }

    interface Presenter: BasePresenter<View>{

        fun checkPrefs()
    }
}