package hr.ferit.igorkuridza.taskie.ui.activities.login

import android.content.Intent
import hr.ferit.igorkuridza.taskie.presentation.LoginActivityPresenter
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.convertCodeToMessage
import hr.ferit.igorkuridza.taskie.common.displayToast
import hr.ferit.igorkuridza.taskie.common.onClick
import hr.ferit.igorkuridza.taskie.prefs.provideSharedPrefs
import hr.ferit.igorkuridza.taskie.ui.activities.main.MainActivity
import hr.ferit.igorkuridza.taskie.ui.activities.register.RegisterActivity
import hr.ferit.igorkuridza.taskie.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity(), LoginContract.View {

    private val presenter by inject<LoginContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun setUpUi() {
        presenter.setView(this)
        login.onClick { signInClicked() }
        goToRegister.onClick { goToRegister() }
    }

    private fun signInClicked() = presenter.onUserLogin(email.text.toString(), password.text.toString())

    override fun onLoginSuccessful() {
        displayToast(R.string.successfullyLoggedInText)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSomethingWentWrong(code: Int) = displayToast(convertCodeToMessage(code))

    override fun onNoInternetConnection() = displayToast(R.string.noInternetText)

    override fun goToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}