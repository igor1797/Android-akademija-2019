package hr.ferit.igorkuridza.taskie.presentation

import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.common.toRoomTask
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.model.request.AddTaskRequest
import hr.ferit.igorkuridza.taskie.networking.interactors.TaskieInteractor
import hr.ferit.igorkuridza.taskie.persistence.TaskRepository
import hr.ferit.igorkuridza.taskie.ui.fragments.addtask.AddTaskContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTaskFragmentPresenter(private val repository: TaskRepository, private val interactor: TaskieInteractor): AddTaskContract.Presenter {

    private lateinit var view: AddTaskContract.View

    override fun setView(view: AddTaskContract.View) {
        this.view = view
    }

    override fun onTaskAdded(title: String, description: String, priorityTask: Int) {
        interactor.save(
            AddTaskRequest(title, description, priorityTask),
            addTaskCallback(title,description,priorityTask)
        )
    }

    private fun addTaskCallback(title: String, description: String, priorityTask: Int): Callback<BackendTask> = object :
        Callback<BackendTask> {
        override fun onFailure(call: Call<BackendTask>?, t: Throwable?) {
            var taskId = "0"
            if (repository.getTasks().isNotEmpty())
                taskId = repository.getTasks().last().id +1
            val task = BackendTask(taskId,title,description,priorityTask,"", false, isCompleted = false, isSent = false)
            repository.addTask(task)
            view.onNoInternetConnection(task)
        }
        override fun onResponse(call: Call<BackendTask>?, response: Response<BackendTask>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()?.let {
                        repository.addTask(toRoomTask(it))
                        view.onAddTaskSuccessful(it)
                    }
                }
            }  else
                view.onSomethingWentWrong(response.code())
        }
    }
}