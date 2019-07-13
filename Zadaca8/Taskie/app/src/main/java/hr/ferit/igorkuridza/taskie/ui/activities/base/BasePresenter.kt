package hr.ferit.igorkuridza.taskie.ui.activities.base

interface BasePresenter<T> {
    fun setView(view: T)
}