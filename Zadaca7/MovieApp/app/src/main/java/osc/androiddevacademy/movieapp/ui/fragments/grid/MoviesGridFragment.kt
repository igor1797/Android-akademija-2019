package osc.androiddevacademy.movieapp.ui.fragments.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movies_grid.*
import osc.androiddevacademy.movieapp.app.App
import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.common.displayToast
import osc.androiddevacademy.movieapp.common.showFragment
import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.networking.BackendFactory
import osc.androiddevacademy.movieapp.presentation.MoviesGridFragmentPresenter
import osc.androiddevacademy.movieapp.ui.adapters.grid.MoviesGridAdapter
import osc.androiddevacademy.movieapp.ui.fragments.pager.MoviesPagerFragment

class MoviesGridFragment : Fragment(),MoviesGridFragmentContract.View {


    private val SPAN_COUNT = 2

    private val gridAdapter by lazy {
        MoviesGridAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    private val presenter: MoviesGridFragmentContract.Presenter by lazy {
        MoviesGridFragmentPresenter(
            BackendFactory.getMovieInteractor(),
            MoviesDatabase.getInstance(App.getAppContext())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_grid, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviesGrid.apply {
            adapter = gridAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }

        presenter.onMoviesRequest()
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.onMoviesRequest()
    }

    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(
            R.id.mainFragmentHolder,
            MoviesPagerFragment.getInstance(
                ArrayList(gridAdapter.getMovies()),
                movie
            ),
            true
        )
    }

    private fun onFavoriteClicked(movie: Movie) {
        presenter.onFavouriteClicked(movie)
    }


    override fun onRequestFail() {
        activity?.displayToast(getString(R.string.failed_toast_text))
    }

    override fun onRequestSuccess(movies: ArrayList<Movie>) {
        gridAdapter.setMovies(movies)
    }

    override fun onFavouriteMovieAdded() {
        gridAdapter.notifyDataSetChanged()
    }

    override fun onFavouriteMovieRemoved() {
        gridAdapter.notifyDataSetChanged()
    }

    companion object{
        fun newInstance() = MoviesGridFragment()
    }

}