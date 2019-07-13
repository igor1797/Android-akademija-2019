package hr.ferit.igorkuridza.taskie.model.response

import hr.ferit.igorkuridza.taskie.model.BackendTask

data class GetTasksResponse(val notes: MutableList<BackendTask>? = mutableListOf())