package hr.ferit.igorkuridza.taskiefragments.ui.fragments.change

import hr.ferit.igorkuridza.taskiefragments.model.Task

interface TaskChangedListener {
    fun onTaskChanged(task: Task)
}