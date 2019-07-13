package hr.ferit.igorkuridza.taskie.ui.activities.register

import hr.ferit.igorkuridza.taskie.model.request.UserDataRequest
import hr.ferit.igorkuridza.taskie.ui.activities.base.BasePresenter

interface RegisterContract {

    interface View{

        fun onNoInternetConnection()

        fun onRegisterSuccessful()

        fun onSomethingWentWrong(code: Int)

        fun goToLogin()
    }

    interface Presenter: BasePresenter<View>{

        fun onRegisterClicked(name: String, email: String, password: String)

        fun onGoToLoginClicked()
    }
}