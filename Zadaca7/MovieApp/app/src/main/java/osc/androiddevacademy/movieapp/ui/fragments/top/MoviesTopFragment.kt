package osc.androiddevacademy.movieapp.ui.fragments.top


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_movies_top.*

import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.app.App
import osc.androiddevacademy.movieapp.common.displayToast
import osc.androiddevacademy.movieapp.common.showFragment
import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.networking.BackendFactory
import osc.androiddevacademy.movieapp.presentation.MoviesTopFragmentPresenter
import osc.androiddevacademy.movieapp.ui.adapters.grid.MoviesGridAdapter
import osc.androiddevacademy.movieapp.ui.fragments.pager.MoviesPagerFragment


class MoviesTopFragment : Fragment(), MoviesTopFragmentContract.View {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_top, container, false)
    }

    private val SPAN_COUNT = 2

    private val presenter: MoviesTopFragmentContract.Presenter by lazy {
        MoviesTopFragmentPresenter(
            BackendFactory.getMovieInteractor(),
            MoviesDatabase.getInstance(App.getAppContext()
            ))
    }

    private val gridTopMoviesAdapter by lazy {
        MoviesGridAdapter(
            { onMovieClicked(it) },
            { onFavoriteClicked(it) })
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
        presenter.onMovieRequest()
    }

    private fun onMovieClicked(movie: Movie) {
        activity?.showFragment(
            R.id.mainFragmentHolder,
            MoviesPagerFragment.getInstance(
                ArrayList(gridTopMoviesAdapter.getMovies()),
                movie
            ), true
        )
    }

    private fun onFavoriteClicked(movie: Movie) {
        presenter.onFavouriteClicked(movie)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topMoviesGrid.apply {
            adapter = gridTopMoviesAdapter
            layoutManager = GridLayoutManager(context, SPAN_COUNT)
        }
    }

    override fun onRequestSuccess(movies: ArrayList<Movie>) {
        gridTopMoviesAdapter.setMovies(movies)
    }

    override fun onRequestFailed() {
        activity?.displayToast(getString(R.string.failed_toast_text))
    }

    override fun onAddFavourite() {
        gridTopMoviesAdapter.notifyDataSetChanged()
    }

    override fun onRemoveFavourite() {
        gridTopMoviesAdapter.notifyDataSetChanged()
    }

    companion object{
        fun newInstance() = MoviesTopFragment()
    }


}
