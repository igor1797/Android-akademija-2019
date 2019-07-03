package hr.ferit.igorkuridza.taskiefragments.ui.fragments.add

import hr.ferit.igorkuridza.taskiefragments.model.Task

interface TaskAddedListener {
    fun onTaskAdded(task: Task)
}