package hr.ferit.igorkuridza.taskiefragments.adapters

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.about.AboutApplicationFragment
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.about.AboutAuthorFragment

class ViewPagerAdapter(manager: FragmentManager, context: Context): FragmentPagerAdapter(manager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private val aboutAuthorFragment = AboutAuthorFragment.newInstance()
    private val aboutApplicationFragment = AboutApplicationFragment.newInstance()

    private val fragmentList = arrayListOf(aboutApplicationFragment,aboutAuthorFragment)
    private val titleList = arrayListOf(
        context.getString(R.string.tabTextApplication),
        context.getString(R.string.tabTextAuthor)
    )

    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int) = titleList[position]

}