package hr.ferit.igorkuridza.dice.diceroller.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import hr.ferit.igorkuridza.dice.diceroller.R

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResourceId())
        setUpUi()
    }

    abstract fun getLayoutResourceId(): Int

    abstract fun setUpUi()
}