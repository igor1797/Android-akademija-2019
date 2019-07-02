package hr.ferit.igorkuridza.dice.bmicalculator.activities

import hr.ferit.igorkuridza.dice.bmicalculator.BMICalculator
import hr.ferit.igorkuridza.dice.bmicalculator.R
import hr.ferit.igorkuridza.dice.bmicalculator.activities.base.BaseActivity
import hr.ferit.igorkuridza.dice.bmicalculator.common.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        buttonCalculateIBM.onClick {
            checkInputs()
        }
    }

    private fun checkInputs(){
        when {
            !areInputsNotEmpty(
                parseEditTextToString(editTextHeight),
                parseEditTextToString(editTextWeight)
                ) -> displayToast(getString(R.string.textCheckEmpty))

            !areInputsWithinBoundaries(
                parseEditTextToFloat(editTextHeight)
                ,parseEditTextToFloat(editTextWeight)
            ) -> displayToast(getString(R.string.textCheckBoundaries))

            else -> {
                setAllVisible()
                setAll()
            }
        }
    }

    private fun setAllVisible(){
        imageViewIBMresult.visible()
        textViewIBMdescriptionResult.visible()
        textViewIBMindexResult.visible()
        textViewIBMbodyMassResult.visible()
    }

    private fun setAll(){
        val weightFloat = parseEditTextToFloat(editTextWeight)
        val heightFloat = parseEditTextToFloat(editTextHeight)

        val bmi = BMICalculator.calculateIBM(heightFloat, weightFloat)

        when {
            bmi<20 -> {
                imageViewIBMresult.setImageResource(R.drawable.pothranjen)
                textViewIBMindexResult.text = bmi.toString()
                textViewIBMbodyMassResult.setText(R.string.pothranjen)
                textViewIBMdescriptionResult.setText(R.string.opisPothranjen)
            }
            bmi in 20.0..25.0 -> {
                imageViewIBMresult.setImageResource(R.drawable.zdrav)
                textViewIBMindexResult.text = bmi.toString()
                textViewIBMbodyMassResult.setText(R.string.zdrav)
                textViewIBMdescriptionResult.setText(R.string.opisZdrav)
            }
            bmi in 25.0..30.0 -> {
                imageViewIBMresult.setImageResource(R.drawable.debeo)
                textViewIBMindexResult.text = bmi.toString()
                textViewIBMbodyMassResult.setText(R.string.debeo)
                textViewIBMdescriptionResult.setText(R.string.opisDebeo)
            }
            else -> {
                imageViewIBMresult.setImageResource(R.drawable.pretio)
                textViewIBMindexResult.text = bmi.toString()
                textViewIBMbodyMassResult.setText(R.string.pretio)
                textViewIBMdescriptionResult.setText(R.string.opisPretio)
            }
        }
    }
}
