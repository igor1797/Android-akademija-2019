package hr.ferit.igorkuridza.dice.diceroller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import hr.ferit.igorkuridza.dice.diceroller.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var list: MutableList<ImageView>

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {

        setImageViews()

        buttonRollDice.setOnClickListener {
            rollDices()
        }

        setImageViewsOnClickListener()

    }

    private fun setImageViews(){
        list = mutableListOf(
            imageView1,
            imageView2,
            imageView3,
            imageView4,
            imageView5,
            imageView6
        )
    }

    private fun rollDices(){
        list.forEach {
            when ((1..6).random()){
                1 -> it.setImageResource(R.drawable.dice1)
                2 -> it.setImageResource(R.drawable.dice2)
                3 -> it.setImageResource(R.drawable.dice3)
                4 -> it.setImageResource(R.drawable.dice4)
                5 -> it.setImageResource(R.drawable.dice5)
                6 -> it.setImageResource(R.drawable.dice6)
            }
        }
    }

    private fun setImageViewsOnClickListener(){
        list.forEach { it ->
            it.setOnClickListener {
                list.remove(it)
            }
        }
    }
}
