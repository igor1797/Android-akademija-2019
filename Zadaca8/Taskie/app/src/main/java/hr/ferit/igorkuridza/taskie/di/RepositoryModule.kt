package hr.ferit.igorkuridza.taskie.di

import hr.ferit.igorkuridza.taskie.persistence.TaskRepository
import hr.ferit.igorkuridza.taskie.persistence.TaskRoomRepository
import hr.ferit.igorkuridza.taskie.prefs.SharedPrefsHelper
import hr.ferit.igorkuridza.taskie.prefs.provideSharedPrefs
import org.koin.dsl.module

val repositoryModule = module {

    factory<TaskRepository> { TaskRoomRepository() }
    factory<SharedPrefsHelper> { provideSharedPrefs() }
}