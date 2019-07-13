package hr.ferit.igorkuridza.taskie.presentation

import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.model.request.UserDataRequest
import hr.ferit.igorkuridza.taskie.model.response.LoginResponse
import hr.ferit.igorkuridza.taskie.networking.interactors.TaskieInteractor
import hr.ferit.igorkuridza.taskie.prefs.SharedPrefsHelper
import hr.ferit.igorkuridza.taskie.ui.activities.login.LoginContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityPresenter(private val interactor: TaskieInteractor, private val prefs: SharedPrefsHelper): LoginContract.Presenter {

    private lateinit var view: LoginContract.View

    override fun setView(view: LoginContract.View) {
        this.view = view
    }

    override fun onGoToRegistrationClicked() {
        view.goToRegister()
    }

    override fun onUserLogin(email: String, password: String) = interactor.login(UserDataRequest(email,password),loginCallback())

    private fun loginCallback(): Callback<LoginResponse> = object : Callback<LoginResponse> {
        override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
            view.onNoInternetConnection()
        }

        override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> {
                        response.body()?.token?.let {prefs.storeUserToken(it)}
                        view.onLoginSuccessful()
                    }
                }
            }else
                view.onSomethingWentWrong(response.code())
        }
    }
}