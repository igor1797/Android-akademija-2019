package hr.ferit.igorkuridza.taskiefragments.ui.activities

import com.google.android.material.bottomnavigation.BottomNavigationView
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.ui.activities.base.BaseActivity
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.about.AboutFragment
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.tasks.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        item ->
            when (item.itemId){
                R.id.item_taskies ->{
                    showFragment(TasksFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.item_about ->{
                    showFragment(AboutFragment.newInstance())
                    return@OnNavigationItemSelectedListener true
                }
                else -> false
            }
    }

    override fun setUpUi() {
        bottom_nav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        showFragment(TasksFragment.newInstance())
    }

}
