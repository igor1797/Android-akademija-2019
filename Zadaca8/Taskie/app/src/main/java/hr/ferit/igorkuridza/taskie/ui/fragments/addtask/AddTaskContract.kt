package hr.ferit.igorkuridza.taskie.ui.fragments.addtask

import hr.ferit.igorkuridza.taskie.model.BackendPriorityTask
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.model.request.AddTaskRequest
import hr.ferit.igorkuridza.taskie.ui.activities.base.BasePresenter

interface AddTaskContract {

    interface View{

        fun onNoInternetConnection(task: BackendTask)

        fun onSomethingWentWrong(code: Int)

        fun onAddTaskSuccessful(task:BackendTask)
    }

    interface Presenter: BasePresenter<View>{

        fun onTaskAdded(title: String, description: String, priorityTask: Int)
    }
}