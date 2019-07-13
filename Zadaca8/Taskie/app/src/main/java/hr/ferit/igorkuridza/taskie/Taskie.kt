package hr.ferit.igorkuridza.taskie

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import hr.ferit.igorkuridza.taskie.di.interactorModule
import hr.ferit.igorkuridza.taskie.di.networkingModule
import hr.ferit.igorkuridza.taskie.di.presentationModule
import hr.ferit.igorkuridza.taskie.di.repositoryModule
import hr.ferit.igorkuridza.taskie.prefs.PREFERENCES_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class Taskie: Application() {

    companion object {
        lateinit var instance: Taskie
            private set

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Taskie)
            modules(listOf(interactorModule, networkingModule, presentationModule, repositoryModule))
        }
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    fun providePreferences(): SharedPreferences = instance.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
}