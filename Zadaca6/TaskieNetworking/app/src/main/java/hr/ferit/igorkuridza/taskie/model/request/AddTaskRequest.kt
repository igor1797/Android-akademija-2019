package hr.ferit.igorkuridza.taskie.model.request

data class AddTaskRequest (val title: String, val content: String, val taskPriority: Int)