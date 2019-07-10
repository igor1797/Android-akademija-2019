package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.MoviesResponse
import osc.androiddevacademy.movieapp.networking.interactors.MovieInteractor
import osc.androiddevacademy.movieapp.ui.fragments.grid.MoviesGridFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesGridFragmentPresenter(private val interactor: MovieInteractor, private val moviesDatabase: MoviesDatabase): MoviesGridFragmentContract.Presenter {

    private lateinit var view: MoviesGridFragmentContract.View

    override fun setView(view: MoviesGridFragmentContract.View) {
        this.view=view
    }

    override fun onFavouriteClicked(movie: Movie) {
        if(!movie.isFavorite){
            movie.isFavorite = !movie.isFavorite
            moviesDatabase.moviesDao().addFavoriteMovie(movie)
            view.onFavouriteMovieAdded()
        }
        else{
            moviesDatabase.moviesDao().deleteFavoriteMovie(movie)
            movie.isFavorite = !movie.isFavorite
            view.onFavouriteMovieRemoved()
        }
    }

    override fun onMoviesRequest() {
        interactor.getPopularMovies(popularMoviesCallback())
    }

    private fun popularMoviesCallback(): Callback<MoviesResponse> =
        object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    view.onRequestFail()
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.isSuccessful) {
                    val favoriteMovies = moviesDatabase.moviesDao().getFavoriteMovies()
                    response.body()?.movies?.forEach {
                        if(favoriteMovies.contains(it))
                            it.isFavorite = true
                    }
                    response.body()?.movies?.let {view.onRequestSuccess(it)}
                }
            }
        }
}