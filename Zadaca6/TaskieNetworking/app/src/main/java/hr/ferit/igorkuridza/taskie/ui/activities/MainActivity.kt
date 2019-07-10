package hr.ferit.igorkuridza.taskie.ui.activities

import android.view.MenuItem
import androidx.fragment.app.Fragment
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.ui.activities.base.BaseActivity
import hr.ferit.igorkuridza.taskie.ui.fragments.about.AboutFragment
import hr.ferit.igorkuridza.taskie.ui.fragments.tasks.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        showFragment(TasksFragment.newInstance())
        bottom_nav.setOnNavigationItemSelectedListener{ openFragment(it) }

    }

    private fun openFragment(menuItem: MenuItem):Boolean{
        var selectedFragment: Fragment? = null
        when (menuItem.itemId) {
            R.id.item_tasks -> selectedFragment = TasksFragment.newInstance()
            R.id.item_about -> selectedFragment = AboutFragment.newInstance()
        }
        if (selectedFragment != null)
            showFragment(selectedFragment)

        return true
    }
}
