package hr.ferit.igorkuridza.taskie.di

import hr.ferit.igorkuridza.taskie.presentation.*
import hr.ferit.igorkuridza.taskie.ui.activities.login.LoginContract
import hr.ferit.igorkuridza.taskie.ui.activities.register.RegisterContract
import hr.ferit.igorkuridza.taskie.ui.activities.splash.SplashContract
import hr.ferit.igorkuridza.taskie.ui.fragments.addtask.AddTaskContract
import hr.ferit.igorkuridza.taskie.ui.fragments.changetask.ChangeTaskContract
import hr.ferit.igorkuridza.taskie.ui.fragments.details.TaskDetailsContract
import hr.ferit.igorkuridza.taskie.ui.fragments.tasks.TasksContract
import org.koin.dsl.module

val presentationModule = module {

    factory<AddTaskContract.Presenter> { AddTaskFragmentPresenter(get(), get()) }
    factory<ChangeTaskContract.Presenter> { ChangeTaskFragmentDialogPresenter(get(), get()) }
    factory<LoginContract.Presenter> { LoginActivityPresenter(get(), get()) }
    factory<RegisterContract.Presenter> { RegisterActivityPresenter(get()) }
    factory<SplashContract.Presenter> { SplashActivityPresenter(get()) }
    factory<TaskDetailsContract.Presenter> { TaskDetailsFragmentPresenter(get(), get()) }
    factory<TasksContract.Presenter> { TasksFragmentPresenter(get(), get()) }
}