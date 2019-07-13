package hr.ferit.igorkuridza.taskie.common

import android.content.Context
import android.widget.Toast

fun Context.displayToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.displayToast(resId: Int) = Toast.makeText(this,getString(resId), Toast.LENGTH_SHORT).show()

