package hr.ferit.igorkuridza.taskie.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.ferit.igorkuridza.taskie.common.TAB_TEXT_APPLICATION
import hr.ferit.igorkuridza.taskie.common.TAB_TEXT_AUTHOR
import hr.ferit.igorkuridza.taskie.ui.fragments.about.AboutApplicationFragment
import hr.ferit.igorkuridza.taskie.ui.fragments.about.AboutAuthorFragment

class ViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {

    private val aboutApplicationFragment = AboutApplicationFragment()
    private val aboutAuthorFragment = AboutAuthorFragment()

    private val fragmentList = arrayOf(aboutApplicationFragment,aboutAuthorFragment)
    private val titleList = arrayOf(TAB_TEXT_APPLICATION, TAB_TEXT_AUTHOR)

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