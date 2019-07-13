package hr.ferit.igorkuridza.taskie.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hr.ferit.igorkuridza.taskie.model.BackendTask


@Database (entities = [BackendTask::class], version = 6, exportSchema = false)
abstract class DaoProvider : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        private var instance: DaoProvider? = null

        fun getInstance(context: Context): DaoProvider{
            if(instance==null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    DaoProvider::class.java,
                    "BackedTaskDb"
                ).fallbackToDestructiveMigration()
                 .allowMainThreadQueries()
                 .build()
            }
            return instance as DaoProvider
        }
    }
}