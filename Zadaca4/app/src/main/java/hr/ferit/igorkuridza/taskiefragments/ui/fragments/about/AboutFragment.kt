package hr.ferit.igorkuridza.taskiefragments.ui.fragments.about

import android.os.Bundle
import android.view.View
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.adapters.ViewPagerAdapter
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : BaseFragment(){

    override fun getLayoutResourceId() = R.layout.fragment_about

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPagerAdapter()
    }

    private fun setUpViewPagerAdapter(){
        viewPager.adapter = ViewPagerAdapter(childFragmentManager,context!!)
        tabs.setupWithViewPager(viewPager)
    }

    companion object{
        fun newInstance() = AboutFragment()
    }
}
