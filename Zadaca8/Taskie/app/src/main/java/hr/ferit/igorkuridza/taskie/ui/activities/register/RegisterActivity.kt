package hr.ferit.igorkuridza.taskie.ui.activities.register

import android.content.Intent
import hr.ferit.igorkuridza.taskie.presentation.RegisterActivityPresenter
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.convertCodeToMessage
import hr.ferit.igorkuridza.taskie.common.displayToast
import hr.ferit.igorkuridza.taskie.common.onClick
import hr.ferit.igorkuridza.taskie.ui.activities.base.BaseActivity
import hr.ferit.igorkuridza.taskie.ui.activities.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject

class RegisterActivity : BaseActivity(), RegisterContract.View {

    private val presenter by inject<RegisterContract.Presenter>()

    override fun getLayoutResourceId(): Int = R.layout.activity_register

    override fun setUpUi() {
        presenter.setView(this)
        register.onClick { signInClicked() }
        goToLogin.onClick { goToLogin() }
    }

    private fun signInClicked() = presenter.onRegisterClicked(name.text.toString(),email.text.toString(),password.text.toString())

    override fun onNoInternetConnection() = displayToast(R.string.noInternetText)

    override fun onRegisterSuccessful() {
        this.displayToast(R.string.successfullyRegisteredText)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSomethingWentWrong(code: Int) = displayToast(convertCodeToMessage(code))

    override fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}