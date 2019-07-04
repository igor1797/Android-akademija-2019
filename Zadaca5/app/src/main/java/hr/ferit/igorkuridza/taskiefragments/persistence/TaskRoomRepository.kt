package hr.ferit.igorkuridza.taskiefragments.persistence

import hr.ferit.igorkuridza.taskiefragments.app.Taskie
import hr.ferit.igorkuridza.taskiefragments.db.DaoProvider
import hr.ferit.igorkuridza.taskiefragments.db.TaskDao
import hr.ferit.igorkuridza.taskiefragments.model.Priority
import hr.ferit.igorkuridza.taskiefragments.model.Task

class TaskRoomRepository: TaskRepository {
    private var db: DaoProvider = DaoProvider.getInstance(Taskie.getAppContext())

    private var taskDao: TaskDao = db.taskDao()

    override fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    override fun getTasks(): MutableList<Task> {
        return taskDao.getAllTasks()
    }

    override fun getTaskById(id: Int): Task {
        return taskDao.getTaskById(id)
    }

    override fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    override fun clearAllTasks() {
        taskDao.deleteAllTasks()
    }

    override fun updateTask(newTitle: String,newDescription: String, newPriority: Priority, taskDbId: Int) {
        taskDao.updateTask(newTitle, newDescription, newPriority, taskDbId)
    }
}
