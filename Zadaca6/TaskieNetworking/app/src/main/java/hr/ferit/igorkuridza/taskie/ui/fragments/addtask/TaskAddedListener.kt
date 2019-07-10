package hr.ferit.igorkuridza.taskie.ui.fragments.addtask

import hr.ferit.igorkuridza.taskie.model.BackendTask

interface TaskAddedListener {
    fun onTaskAdded(task: BackendTask)
}