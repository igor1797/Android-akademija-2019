package hr.ferit.igorkuridza.taskie.ui.fragments.about


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.ui.adapters.ViewPagerAdapter
import hr.ferit.igorkuridza.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*


class AboutFragment : BaseFragment() {
    override fun getLayoutResourceId() = R.layout.fragment_about

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
    }

    private fun setUpViewPager(){
        viewPager.adapter = ViewPagerAdapter(childFragmentManager)
        tabs.setupWithViewPager(viewPager)
    }

    companion object{
        fun newInstance(): Fragment {
            return AboutFragment()
        }
    }
}