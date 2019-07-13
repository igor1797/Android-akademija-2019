package hr.ferit.igorkuridza.taskie.networking.interactors

import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.model.request.AddTaskRequest
import hr.ferit.igorkuridza.taskie.model.request.EditTaskRequest
import hr.ferit.igorkuridza.taskie.model.request.UserDataRequest
import hr.ferit.igorkuridza.taskie.model.response.*
import hr.ferit.igorkuridza.taskie.networking.TaskieApiService
import retrofit2.Callback

class TaskieInteractorImpl(private val apiService: TaskieApiService) : TaskieInteractor {

    override fun getTasks(taskieResponseCallback: Callback<GetTasksResponse>) {
        apiService.getTasks().enqueue(taskieResponseCallback)
    }

    override fun register(request: UserDataRequest, registerCallback: Callback<RegisterResponse>) {
        apiService.register(request).enqueue(registerCallback)
    }

    override fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>) {
        apiService.login(request).enqueue(loginCallback)
    }

    override fun save(request: AddTaskRequest, saveCallback: Callback<BackendTask>) {
        apiService.save(request).enqueue(saveCallback)
    }

    override fun delete(id: String, deleteCallback: Callback<DeleteTaskResponse>) {
        apiService.deleteTask(id).enqueue(deleteCallback)
    }

    override fun getTaskById(id: String, getTaskCallback: Callback<BackendTask>){
        apiService.getTaskById(id).enqueue(getTaskCallback)
    }

    override fun editTask(request: EditTaskRequest, editCallback: Callback<EditTaskResponse>) {
        apiService.editTask(request).enqueue(editCallback)
    }
}
