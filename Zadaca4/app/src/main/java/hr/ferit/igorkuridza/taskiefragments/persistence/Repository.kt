package hr.ferit.igorkuridza.taskiefragments.persistence

import hr.ferit.igorkuridza.taskiefragments.model.Priority
import hr.ferit.igorkuridza.taskiefragments.model.Task

object Repository {

    private val tasks = mutableListOf<Task>()
    private var currentId = 0

    fun save(title: String, description: String, priority: Priority): Task{
        val task = Task(currentId,title, description, priority)
        task.id = currentId
        tasks.add(task)
        currentId++
        return task
    }

    fun get(taskId: Int): Task{
        return tasks.first { it.id == taskId }
    }

    fun getAllTasks() = tasks
}