package hr.ferit.igorkuridza.taskie.presentation

import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.model.request.EditTaskRequest
import hr.ferit.igorkuridza.taskie.model.response.EditTaskResponse
import hr.ferit.igorkuridza.taskie.networking.interactors.TaskieInteractor
import hr.ferit.igorkuridza.taskie.persistence.TaskRepository
import hr.ferit.igorkuridza.taskie.ui.fragments.changetask.ChangeTaskContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangeTaskFragmentDialogPresenter(private val interactor: TaskieInteractor, private val repository: TaskRepository): ChangeTaskContract.Presenter {

    private lateinit var view: ChangeTaskContract.View

    override fun setView(view: ChangeTaskContract.View) {
        this.view = view
    }

    override fun onEditTask(id: String, title: String, content: String, priority: Int) {
        interactor.editTask(EditTaskRequest(id,title,content,priority), editTaskCallback(id, title, content, priority))
    }

    private fun editTaskCallback(id: String, title: String, content: String, priority: Int): Callback<EditTaskResponse> = object : Callback<EditTaskResponse> {
        override fun onFailure(call: Call<EditTaskResponse>, t: Throwable) {
            view.onNoInternetConnection()
        }

        override fun onResponse(call: Call<EditTaskResponse>, response: Response<EditTaskResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let {
                        repository.updateTask(title, content, priority, id)
                        view.onChangeTaskSuccessful(it)
                    }
                }
            } else
                view.onSomethingWentWrong(response.code())
        }
    }
}