package hr.ferit.igorkuridza.taskiefragments.common

import android.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import hr.ferit.igorkuridza.taskiefragments.R

fun showAlertDialog(fragmentActivity: FragmentActivity?, title: String, icon: Int, positiveButtonListener: () -> Unit?, negativeButtonListener: () -> Unit?){
    fragmentActivity?.let {
        AlertDialog.Builder(it)
            .setTitle(title)
            .setIcon(icon)
            .setPositiveButton(it.getString(R.string.alertDialogTextPositiveButton)){
                _, _ -> positiveButtonListener()
            }
            .setNegativeButton(it.getString(R.string.alertDialogTextNegativeButton)){
                _, _ -> negativeButtonListener()
            }
            .show()
    }
}