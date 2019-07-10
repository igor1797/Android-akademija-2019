package osc.androiddevacademy.movieapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_movies.*
import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.common.showFragment
import osc.androiddevacademy.movieapp.ui.fragments.favourite.MoviesFavouriteFragment
import osc.androiddevacademy.movieapp.ui.fragments.grid.MoviesGridFragment
import osc.androiddevacademy.movieapp.ui.fragments.top.MoviesTopFragment

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        initMoviesGridFragment()
        bottomNav.setOnNavigationItemSelectedListener { openFragment(it) }
    }

    private fun initMoviesGridFragment(){
        showFragment(R.id.mainFragmentHolder,
            MoviesGridFragment()
        )
    }

    private fun openFragment(menuItem: MenuItem): Boolean{
        var selected : Fragment? = null
        when(menuItem.itemId){
            R.id.menuItemPopular -> selected = MoviesGridFragment.newInstance()
            R.id.menuItemFavourite -> selected = MoviesFavouriteFragment.newInstance()
            R.id.menuItemTop -> selected = MoviesTopFragment.newInstance()
        }
        if(selected!=null)
            showFragment(R.id.mainFragmentHolder, selected)
        return true
    }
}
