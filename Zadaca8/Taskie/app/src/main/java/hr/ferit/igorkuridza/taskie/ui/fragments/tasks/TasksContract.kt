package hr.ferit.igorkuridza.taskie.ui.fragments.tasks

import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.ui.activities.base.BasePresenter

interface TasksContract {

    interface View{
        fun onNoInternetConnection(tasks: MutableList<BackendTask>)

        fun onSomethingWentWrong(code: Int)

        fun onDeleteTaskSuccessful(id: String)

        fun onGetTasksSuccessful(tasks: MutableList<BackendTask>)

        fun onSaveOfflineTaskSuccessful()

    }

    interface Presenter: BasePresenter<View>{
        fun onGetAllTasks()

        fun onDeleteTask(task: BackendTask)

        fun onSaveOfflineTasks()

        fun onClearAllTasks()
    }
}