package hr.ferit.igorkuridza.taskie.di

import hr.ferit.igorkuridza.taskie.networking.interactors.TaskieInteractor
import hr.ferit.igorkuridza.taskie.networking.interactors.TaskieInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory<TaskieInteractor> { TaskieInteractorImpl(get()) }
}