package hr.ferit.igorkuridza.dice.bmicalculator.common

import android.content.Context
import android.widget.Toast

fun Context.displayToast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()