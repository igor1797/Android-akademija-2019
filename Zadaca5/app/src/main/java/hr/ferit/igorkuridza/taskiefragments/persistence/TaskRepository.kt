package hr.ferit.igorkuridza.taskiefragments.persistence

import hr.ferit.igorkuridza.taskiefragments.model.Priority
import hr.ferit.igorkuridza.taskiefragments.model.Task

interface TaskRepository {
    fun addTask(task: Task)
    fun getTasks(): MutableList<Task>
    fun getTaskById(id: Int): Task
    fun deleteTask(task: Task)
    fun clearAllTasks()
    fun updateTask(newTitle: String, newDescription: String, newPriority: Priority, taskDbId: Int)
}