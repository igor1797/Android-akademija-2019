package hr.ferit.igorkuridza.taskiefragments.persistence

import androidx.room.TypeConverter
import hr.ferit.igorkuridza.taskiefragments.model.Priority

class Converters {

    companion object{

        @TypeConverter
        @JvmStatic
        fun fromPriority(priority: Priority): String {
            return priority.name
        }

        @TypeConverter
        @JvmStatic
        fun toPriority(priority: String): Priority {
            return Priority.valueOf(priority)
        }
    }
}