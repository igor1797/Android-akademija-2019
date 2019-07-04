package hr.ferit.igorkuridza.taskiefragments.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey (autoGenerate = true)
    var id: Int? = null,
    val title: String,
    val description: String,
    val priority: Priority
)