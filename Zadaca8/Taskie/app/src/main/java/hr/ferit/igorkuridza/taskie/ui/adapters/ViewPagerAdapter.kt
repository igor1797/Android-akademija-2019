package hr.ferit.igorkuridza.taskie.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.ui.fragments.about.AboutApplicationFragment
import hr.ferit.igorkuridza.taskie.ui.fragments.about.AboutAuthorFragment

class ViewPagerAdapter(manager: FragmentManager, context: Context): FragmentPagerAdapter(manager) {

    private val aboutApplicationFragment = AboutApplicationFragment()
    private val aboutAuthorFragment = AboutAuthorFragment()
    private val tabTextAuthor = context.getText(R.string.tabTextAuthor)
    private val tabTextApplication = context.getText(R.string.tabTextApplication)

    private val fragmentList = arrayOf(aboutApplicationFragment,aboutAuthorFragment)
    private val titleList = arrayOf(tabTextAuthor, tabTextApplication)

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return  fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}