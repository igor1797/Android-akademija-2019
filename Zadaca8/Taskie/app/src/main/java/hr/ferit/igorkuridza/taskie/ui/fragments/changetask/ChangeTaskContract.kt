package hr.ferit.igorkuridza.taskie.ui.fragments.changetask

import hr.ferit.igorkuridza.taskie.model.response.EditTaskResponse
import hr.ferit.igorkuridza.taskie.ui.activities.base.BasePresenter

interface ChangeTaskContract {

    interface View{

        fun onNoInternetConnection()

        fun onSomethingWentWrong(code: Int)

        fun onChangeTaskSuccessful(editTaskResponse: EditTaskResponse)
    }

    interface Presenter: BasePresenter<View>{

        fun onEditTask(id: String, title: String, content: String, priority: Int)
    }
}