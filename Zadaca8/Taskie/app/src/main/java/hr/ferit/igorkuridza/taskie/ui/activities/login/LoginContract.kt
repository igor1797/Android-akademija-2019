package hr.ferit.igorkuridza.taskie.ui.activities.login

import hr.ferit.igorkuridza.taskie.model.response.LoginResponse
import hr.ferit.igorkuridza.taskie.ui.activities.base.BasePresenter

interface LoginContract {

    interface View{

        fun onLoginSuccessful()

        fun onSomethingWentWrong(code: Int)

        fun onNoInternetConnection()

        fun goToRegister()

    }

    interface Presenter: BasePresenter<View>{

        fun onGoToRegistrationClicked()

        fun onUserLogin(email: String, password: String)
    }
}