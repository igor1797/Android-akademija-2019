package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.MoviesResponse
import osc.androiddevacademy.movieapp.networking.interactors.MovieInteractor
import osc.androiddevacademy.movieapp.ui.fragments.top.MoviesTopFragmentContract

class MoviesTopFragmentPresenter (private val interactor: MovieInteractor, private val moviesDatabase: MoviesDatabase) : MoviesTopFragmentContract.Presenter{

    private lateinit var view: MoviesTopFragmentContract.View

    override fun setView(view: MoviesTopFragmentContract.View) {
        this.view = view
    }

    override fun onMovieRequest() {
        interactor.getTopMovies(topMoviesCallback())
    }

    private fun topMoviesCallback(): retrofit2.Callback<MoviesResponse> =
        object : retrofit2.Callback<MoviesResponse> {
            override fun onFailure(call: retrofit2.Call<MoviesResponse>, t: Throwable) {
                view.onRequestFailed()
            }

            override fun onResponse(
                call: retrofit2.Call<MoviesResponse>,
                response: retrofit2.Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val favoriteMovies = moviesDatabase.moviesDao().getFavoriteMovies()
                    response.body()?.movies?.forEach{
                        if(favoriteMovies.contains(it))
                            it.isFavorite = true
                    }
                    response.body()?.movies?.let { view.onRequestSuccess(it) }
                }
            }
        }


    override fun onFavouriteClicked(movie: Movie) {
        if (!movie.isFavorite){
            movie.isFavorite = !movie.isFavorite
            moviesDatabase.moviesDao().addFavoriteMovie(movie)
            view.onAddFavourite()
        } else {
            moviesDatabase.moviesDao().deleteFavoriteMovie(movie)
            movie.isFavorite = !movie.isFavorite
            view.onRemoveFavourite()
        }
    }
}