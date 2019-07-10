package osc.androiddevacademy.movieapp.ui.fragments.favourite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movies_favourite.*

import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.app.App
import osc.androiddevacademy.movieapp.common.displayToast
import osc.androiddevacademy.movieapp.common.showFragment
import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.presentation.MoviesFavouriteFragmentPresenter
import osc.androiddevacademy.movieapp.ui.adapters.grid.MoviesGridAdapter
import osc.androiddevacademy.movieapp.ui.fragments.pager.MoviesPagerFragment

class MoviesFavouriteFragment : Fragment() , MoviesFavouriteFragmentContract.View {

    private val SPAN_COUNT = 2

    private val presenter: MoviesFavouriteFragmentContract.Presenter by lazy {
        MoviesFavouriteFragmentPresenter(MoviesDatabase.getInstance(App.getAppContext()))
    }

    private val gridFavouriteAdapter by lazy {
        MoviesGridAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.onRequestFavouriteMovies()
    }

    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(
            R.id.mainFragmentHolder,
            MoviesPagerFragment.getInstance(
                ArrayList(gridFavouriteAdapter.getMovies()),
                movie
            ), true
        )
    }

    private fun onFavoriteClicked(movie: Movie) {
        presenter.onFavouriteClicked(movie)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteMoviesGrid.apply {
            adapter = gridFavouriteAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    override fun onMoviesReceived(movies: List<Movie>) {
        gridFavouriteAdapter.setMovies(movies)
    }

    override fun onFavouriteRemoved(movie: Movie) {
        gridFavouriteAdapter.removeMovie(movie)
    }

    override fun onEmptyListReceived() {
        activity?.displayToast(getString(R.string.onEmptyList_toast_text))
    }

    companion object {
        fun newInstance(): MoviesFavouriteFragment =
            MoviesFavouriteFragment()
    }
}
