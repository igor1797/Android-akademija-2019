package hr.ferit.igorkuridza.taskie.common

import android.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import hr.ferit.igorkuridza.taskie.R

fun showDialog(activity: FragmentActivity?, title: String, positiveButtonListener: () -> Unit){
    activity?.let {
        AlertDialog.Builder(it)
            .setTitle(title)
            .setIcon(R.drawable.ic_about)
            .setPositiveButton(R.string.positiveAlertButtonText){_,_ -> positiveButtonListener()}
            .setNegativeButton(R.string.negativeAlertButtonText){dialog,_ -> dialog.cancel()}
            .show()
    }
}

fun showDialog(activity: FragmentActivity?, title: String, positiveButtonListener: () -> Unit, negativeButtonListener: ()  -> Unit){
    activity?.let {
        AlertDialog.Builder(it)
            .setTitle(title)
            .setIcon(R.drawable.ic_about)
            .setPositiveButton(R.string.positiveAlertButtonText){_,_ -> positiveButtonListener()}
            .setNegativeButton(R.string.negativeAlertButtonText){_,_ -> negativeButtonListener()}
            .show()
    }
}

