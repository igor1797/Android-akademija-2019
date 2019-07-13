package hr.ferit.igorkuridza.taskie.networking.interactors

import hr.ferit.igorkuridza.taskie.model.BackendTask
import hr.ferit.igorkuridza.taskie.model.request.AddTaskRequest
import hr.ferit.igorkuridza.taskie.model.request.EditTaskRequest
import hr.ferit.igorkuridza.taskie.model.request.UserDataRequest
import hr.ferit.igorkuridza.taskie.model.response.*
import retrofit2.Callback

interface TaskieInteractor {

    fun getTasks(taskieResponseCallback: Callback<GetTasksResponse>)

    fun register(request: UserDataRequest, registerCallback: Callback<RegisterResponse>)

    fun login(request: UserDataRequest, loginCallback: Callback<LoginResponse>)

    fun save(request: AddTaskRequest, saveCallback: Callback<BackendTask>)

    fun delete(id: String, deleteCallback: Callback<DeleteTaskResponse>)

    fun editTask(request: EditTaskRequest, editCallback: Callback<EditTaskResponse>)

    fun getTaskById(id: String, getTaskCallback: Callback<BackendTask>)
}