package hr.ferit.igorkuridza.taskie.presentation

import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.networking.interactors.TaskieInteractor
import hr.ferit.igorkuridza.taskie.persistence.TaskRepository
import hr.ferit.igorkuridza.taskie.ui.fragments.details.TaskDetailsContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskDetailsFragmentPresenter(private val repository: TaskRepository, private val interactor: TaskieInteractor): TaskDetailsContract.Presenter {

    private lateinit var view: TaskDetailsContract.View

    override fun setView(view: TaskDetailsContract.View) {
        this.view = view
    }

    override fun onGetTaskById(id: String): BackendTask {
        return repository.getTask(id)
    }

    override fun onTryDisplayTask(id: String) {
        interactor.getTaskById(id, getTaskCallback())
    }

    private fun getTaskCallback(): Callback<BackendTask> = object : Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
            view.onNoInternetConnection()
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> view.onGetTaskSuccessful(response.body())
                }
            }
            else
                view.onSomethingWentWrong(response.code())
        }
    }
}