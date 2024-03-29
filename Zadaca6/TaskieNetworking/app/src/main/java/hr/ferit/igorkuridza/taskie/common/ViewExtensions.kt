package hr.ferit.igorkuridza.taskie.common

import android.view.View

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}

fun View.invisible(){
    visibility = View.INVISIBLE
}

fun View.onClick(onClick: () -> Unit) {
    setOnClickListener { onClick() }
}