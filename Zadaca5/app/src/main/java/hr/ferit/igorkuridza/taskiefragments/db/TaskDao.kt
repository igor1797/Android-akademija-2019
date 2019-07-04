package hr.ferit.igorkuridza.taskiefragments.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import hr.ferit.igorkuridza.taskiefragments.model.Priority
import hr.ferit.igorkuridza.taskiefragments.model.Task

@Dao
interface TaskDao {
    @Query("Select * FROM Task")
    fun getAllTasks(): MutableList<Task>

    @Query("SELECT * FROM Task WHERE id= :taskId")
    fun getTaskById(taskId: Int): Task

    @Insert(onConflict = IGNORE)
    fun insertTask(task: Task)

    @Delete()
    fun deleteTask(task: Task)

    @Query ("UPDATE Task SET title= :taskTitle,description= :taskDescription, priority= :taskPriority WHERE id= :taskId")
    fun updateTask(taskTitle: String, taskDescription: String, taskPriority: Priority, taskId: Int)

    @Query("DELETE from Task")
    fun deleteAllTasks()
}