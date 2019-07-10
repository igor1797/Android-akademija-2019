package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.ui.fragments.favourite.MoviesFavouriteFragmentContract

class MoviesFavouriteFragmentPresenter(private val moviesDatabase: MoviesDatabase) : MoviesFavouriteFragmentContract.Presenter{

    private lateinit var view: MoviesFavouriteFragmentContract.View

    override fun setView(view: MoviesFavouriteFragmentContract.View) {
        this.view = view
    }

    override fun onRequestFavouriteMovies() {
        val favouriteMovies = moviesDatabase.moviesDao().getFavoriteMovies()
        if (favouriteMovies.isEmpty())
            view.onEmptyListReceived()
        else {
            setMoviesFavourite(favouriteMovies)
            view.onMoviesReceived(favouriteMovies)
        }

    }

    override fun onFavouriteClicked(movie: Movie) {
        moviesDatabase.moviesDao().deleteFavoriteMovie(movie)
        movie.isFavorite = !movie.isFavorite
        view.onFavouriteRemoved(movie)
    }

    private fun setMoviesFavourite(movies: List<Movie>) = movies.forEach { it.isFavorite = true }
}