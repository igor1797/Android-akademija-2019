package hr.ferit.igorkuridza.taskie.ui.activities

import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.common.*
import hr.ferit.igorkuridza.taskie.ui.activities.base.BaseActivity
import hr.ferit.igorkuridza.taskie.ui.fragments.details.TaskDetailsFragment
import kotlinx.android.synthetic.main.activity_main.*


class ContainerActivity: BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {

        val screenType = intent.getStringExtra(EXTRA_SCREEN_TYPE)
        val id = intent.getStringExtra(EXTRA_TASK_ID)
        if (screenType.isNotEmpty()) {
            when (screenType) {
                SCREEN_TASK_DETAILS -> {
                    showFragment(TaskDetailsFragment.newInstance(id))
                }
            }
        } else {
            finish()
        }
        bottom_nav.invisible()
    }

    companion object{
        const val SCREEN_TASK_DETAILS = "task_details"
    }
}

