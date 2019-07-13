package hr.ferit.igorkuridza.taskie.persistence

import hr.ferit.igorkuridza.taskie.model.BackendTask

interface TaskRepository {

    fun addTask(task: BackendTask)
    fun getTask(id: String): BackendTask
    fun getTasks(): List<BackendTask>
    fun deleteTask(task: BackendTask)
    fun clearAllTasks()
    fun updateTask(newTitle: String,newContent: String,newPriority: Int,id: String)
    fun sortByPriority(): List<BackendTask>
}