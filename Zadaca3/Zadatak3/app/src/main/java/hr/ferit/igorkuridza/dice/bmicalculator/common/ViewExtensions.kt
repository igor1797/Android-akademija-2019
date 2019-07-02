package hr.ferit.igorkuridza.dice.bmicalculator.common

import android.view.View

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.onClick(onClick: () -> Unit){
    setOnClickListener { onClick() }
}