package hr.ferit.igorkuridza.taskiefragments.common

import android.content.Context
import android.widget.Toast

fun Context.displayToast(resourceId: Int) = Toast.makeText(this, getString(resourceId), Toast.LENGTH_SHORT).show()