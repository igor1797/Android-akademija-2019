package hr.ferit.igorkuridza.taskie.presentation

import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.model.request.UserDataRequest
import hr.ferit.igorkuridza.taskie.model.response.RegisterResponse
import hr.ferit.igorkuridza.taskie.networking.interactors.TaskieInteractor
import hr.ferit.igorkuridza.taskie.ui.activities.register.RegisterContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivityPresenter(private val interactor: TaskieInteractor): RegisterContract.Presenter {

    private lateinit var view: RegisterContract.View

    override fun setView(view: RegisterContract.View) {
        this.view = view
    }

    override fun onGoToLoginClicked() {
        view.goToLogin()
    }

    override fun onRegisterClicked(name: String, email: String, password: String) =
        interactor.register(UserDataRequest(email,password,name), registerCallback())

    private fun registerCallback(): Callback<RegisterResponse> = object : Callback<RegisterResponse> {
        override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
            view.onNoInternetConnection()
        }

        override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> view.onRegisterSuccessful()
                }
            }else
                view.onSomethingWentWrong(response.code())
        }
    }
}