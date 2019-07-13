package hr.ferit.igorkuridza.taskie.ui.fragments.details

import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.ui.activities.base.BasePresenter

interface TaskDetailsContract {

    interface View{

        fun onNoInternetConnection()

        fun onSomethingWentWrong(code: Int)

        fun onGetTaskSuccessful(task: BackendTask?)
    }

    interface Presenter: BasePresenter<View>{

        fun onGetTaskById(id: String): BackendTask

        fun onTryDisplayTask(id: String)
    }
}