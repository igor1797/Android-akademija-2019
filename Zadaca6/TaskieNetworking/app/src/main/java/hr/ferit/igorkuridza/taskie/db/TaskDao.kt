package hr.ferit.igorkuridza.taskie.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import hr.ferit.igorkuridza.taskie.model.BackendTask

@Dao
interface TaskDao {

    @Query ("Select * FROM BackendTask")
    fun getAllTasks(): List<BackendTask>

    @Query("SELECT * FROM BackendTask WHERE id= :id")
    fun getTask(id: String): BackendTask

    @Insert(onConflict = IGNORE)
    fun insertTask(task: BackendTask)

    @Delete()
    fun deleteTask(task: BackendTask)

    @Query("DELETE from BackendTask")
    fun deleteAllTasks()

    @Query ("UPDATE BackendTask SET title= :taskTitle, content= :taskContent, taskPriority= :taskPriority WHERE id= :id")
    fun updateTask(taskTitle: String,taskContent: String,taskPriority: Int,id: String)

    @Query("SELECT * FROM BackendTask ORDER BY taskPriority  DESC ")
    fun sortByPriority(): List<BackendTask>

}