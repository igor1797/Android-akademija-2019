package hr.ferit.igorkuridza.taskie.ui.activities

import android.content.Intent
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.common.displayToast
import hr.ferit.igorkuridza.taskie.common.onClick
import hr.ferit.igorkuridza.taskie.model.request.UserDataRequest
import hr.ferit.igorkuridza.taskie.model.response.RegisterResponse
import hr.ferit.igorkuridza.taskie.networking.BackendFactory
import hr.ferit.igorkuridza.taskie.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : BaseActivity() {

    private val interactor = BackendFactory.getTaskieInteractor()

    override fun getLayoutResourceId(): Int = R.layout.activity_register

    override fun setUpUi() {
        register.onClick { signInClicked() }
        goToLogin.onClick { goToLoginClicked() }
    }

    private fun signInClicked() {
        interactor.register(
            UserDataRequest(
                email = email.text.toString(),
                password = password.text.toString(),
                name = name.text.toString()
            ), registerCallback()
        )
    }

    private fun registerCallback(): Callback<RegisterResponse> = object : Callback<RegisterResponse> {
        override fun onFailure(call: Call<RegisterResponse>?, t: Throwable?) {
                displayToast("No internet connection")
        }

        override fun onResponse(call: Call<RegisterResponse>?, response: Response<RegisterResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> handleOkResponse()
                    else -> handleSomethingWentWrong()
                }
            }
        }
    }

    private fun goToLoginClicked() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleOkResponse() {
        this.displayToast("Successfully registered")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleSomethingWentWrong() = this.displayToast("Something went wrong!")
}