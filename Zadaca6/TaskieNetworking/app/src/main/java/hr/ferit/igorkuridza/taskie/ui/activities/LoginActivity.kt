package hr.ferit.igorkuridza.taskie.ui.activities

import android.content.Intent
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.common.displayToast
import hr.ferit.igorkuridza.taskie.common.onClick
import hr.ferit.igorkuridza.taskie.model.request.UserDataRequest
import hr.ferit.igorkuridza.taskie.model.response.LoginResponse
import hr.ferit.igorkuridza.taskie.networking.BackendFactory
import hr.ferit.igorkuridza.taskie.prefs.provideSharedPrefs
import hr.ferit.igorkuridza.taskie.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {

    private val interactor = BackendFactory.getTaskieInteractor()
    private val prefs = provideSharedPrefs()

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun setUpUi() {
        login.onClick { signInClicked() }
        goToRegister.onClick { goToRegistrationClicked() }
    }

    private fun signInClicked() {
        interactor.login(
            request = UserDataRequest(password = password.text.toString(), email = email.text.toString()),
            loginCallback = loginCallback()
        )
    }

    private fun loginCallback(): Callback<LoginResponse> = object : Callback<LoginResponse> {
        override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
        }

        override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse(response.body())
                    else -> handleSomethingWentWrong()
                }
            }
        }
    }

    private fun goToRegistrationClicked() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleOkResponse(loginReponse: LoginResponse?) {
        this.displayToast("Successfully logged in!")
        loginReponse?.token?.let { prefs.storeUserToken(it) }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleSomethingWentWrong() = this.displayToast("Something went wrong!")
}