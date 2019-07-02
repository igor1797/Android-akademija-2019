package hr.ferit.igorkuridza.dice.bmicalculator.common

import android.widget.EditText

fun parseEditTextToFloat(editText: EditText) = java.lang.Float.parseFloat((editText.text).toString())

fun parseEditTextToString(editText: EditText) = editText.text.toString()