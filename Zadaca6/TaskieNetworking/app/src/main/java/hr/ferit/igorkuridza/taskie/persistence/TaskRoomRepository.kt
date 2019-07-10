package hr.ferit.igorkuridza.taskie.persistence

import hr.ferit.igorkuridza.taskie.Taskie
import hr.ferit.igorkuridza.taskie.db.DaoProvider
import hr.ferit.igorkuridza.taskie.db.TaskDao
import hr.ferit.igorkuridza.taskie.model.BackendTask


class TaskRoomRepository: TaskRepository {
    private var db: DaoProvider = DaoProvider.getInstance(Taskie.getAppContext())

    private var taskDao: TaskDao = db.taskDao()

    override fun addTask(task: BackendTask) {
        taskDao.insertTask(task)
    }

    override fun getTasks(): List<BackendTask> {
        return taskDao.getAllTasks()
    }

    override fun getTask(id: String): BackendTask {
        return taskDao.getTask(id)
    }

    override fun deleteTask(task: BackendTask) {
        taskDao.deleteTask(task)
    }

    override fun clearAllTasks() {
        taskDao.deleteAllTasks()
    }

    override fun updateTask(newTitle: String, newContent: String, newPriority: Int, id: String) {
        taskDao.updateTask(newTitle, newContent, newPriority, id)
    }


    override fun sortByPriority(): List<BackendTask> {
        return taskDao.sortByPriority()
    }
}
