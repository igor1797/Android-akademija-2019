package hr.ferit.igorkuridza.taskiefragments.ui.fragments.about

import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.ui.fragments.base.BaseFragment


class AboutAuthorFragment : BaseFragment() {

    override fun getLayoutResourceId() = R.layout.fragment_about_author

    companion object{
        fun newInstance() = AboutAuthorFragment()
    }
}
