package hr.ferit.igorkuridza.taskie.presentation

import hr.ferit.igorkuridza.taskie.common.RESPONSE_OK
import hr.ferit.igorkuridza.taskie.common.toRoomTask
import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.model.request.AddTaskRequest
import hr.ferit.igorkuridza.taskie.model.response.DeleteTaskResponse
import hr.ferit.igorkuridza.taskie.model.response.GetTasksResponse
import hr.ferit.igorkuridza.taskie.networking.interactors.TaskieInteractor
import hr.ferit.igorkuridza.taskie.persistence.TaskRepository
import hr.ferit.igorkuridza.taskie.ui.fragments.tasks.TasksContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksFragmentPresenter(private val repository: TaskRepository,private val interactor: TaskieInteractor): TasksContract.Presenter {

    private lateinit var view: TasksContract.View

    override fun setView(view: TasksContract.View) {
        this.view = view
    }

    override fun onGetAllTasks() {
        interactor.getTasks(getTasksCallback())
    }

    private fun getTasksCallback(): Callback<GetTasksResponse> = object : Callback<GetTasksResponse> {
        override fun onFailure(call: Call<GetTasksResponse>?, t: Throwable?) {
            view.onNoInternetConnection(repository.getTasks() as MutableList<BackendTask>)
        }

        override fun onResponse(call: Call<GetTasksResponse>?, response: Response<GetTasksResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> response.body()!!.notes?.let {
                        it.forEach{ backendTask -> repository.addTask(toRoomTask(backendTask))}
                        view.onGetTasksSuccessful(it)
                    }
                }
            }else
                view.onSomethingWentWrong(response.code())
        }
    }

    override fun onDeleteTask(task: BackendTask) {
        interactor.delete(task.id, deleteTaskCallback(task.id))
    }

    override fun onClearAllTasks() {
        val data = repository.getTasks()
        for (task in data) {
            interactor.delete(task.id, deleteTaskCallback(task.id))
        }
    }

    private fun deleteTaskCallback(id: String): Callback<DeleteTaskResponse> = object  :
        Callback<DeleteTaskResponse> {
        override fun onFailure(call: Call<DeleteTaskResponse>, t: Throwable) {
            view.onNoInternetConnection(repository.getTasks() as MutableList<BackendTask>)
        }

        override fun onResponse(call: Call<DeleteTaskResponse>, response: Response<DeleteTaskResponse>) {
            if (response.isSuccessful) {
                when (response.code()) {
                    RESPONSE_OK -> {
                        val task = repository.getTask(id)
                        repository.deleteTask(task)
                        view.onDeleteTaskSuccessful(id)
                    }
                }
            }else
                view.onSomethingWentWrong(response.code())
        }
    }

    override fun onSaveOfflineTasks() {
        repository.getTasks().forEach { if(it.isSent == false){
            interactor.save(AddTaskRequest(it.title, it.content,it.taskPriority),addOfflineTaskCallback(it.id))
        }}
    }

    private fun addOfflineTaskCallback(id: String): Callback<BackendTask> = object : Callback<BackendTask>{
        override fun onFailure(call: Call<BackendTask>, t: Throwable) {
            view.onNoInternetConnection(repository.getTasks() as MutableList<BackendTask>)
        }

        override fun onResponse(call: Call<BackendTask>, response: Response<BackendTask>) {
            if(response.isSuccessful){
                when(response.code()){
                    RESPONSE_OK -> response.body()?.let {
                        repository.getTask(id).let { backendTask ->
                            repository.deleteTask(backendTask)
                        }
                        repository.addTask(it)
                        view.onSaveOfflineTaskSuccessful()
                    }
                }
            }else
                view.onSomethingWentWrong(response.code())
        }
    }
}