package hr.ferit.igorkuridza.taskie.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hr.ferit.igorkuridza.taskie.presentation.SplashActivityPresenter
import hr.ferit.igorkuridza.taskie.prefs.provideSharedPrefs
import hr.ferit.igorkuridza.taskie.ui.activities.main.MainActivity
import hr.ferit.igorkuridza.taskie.ui.activities.register.RegisterActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity(), SplashContract.View {

    private val presenter by inject<SplashContract.Presenter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.setView(this)
        checkPrefs()
    }

    private fun checkPrefs() {
        presenter.checkPrefs()
    }

    override fun onEmptyToken() {
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    override fun onNotEmptyToken() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}