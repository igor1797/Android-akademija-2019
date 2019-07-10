package hr.ferit.igorkuridza.taskie.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class BackendTask (
    @PrimaryKey
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("taskPriority") val taskPriority: Int,
    @SerializedName("userId") val userId: String?,
    @SerializedName("isFavorite") val isFavorite: Boolean,
    @SerializedName("isCompleted") val isCompleted: Boolean,
    @SerializedName("sent") var isSent: Boolean?
)
