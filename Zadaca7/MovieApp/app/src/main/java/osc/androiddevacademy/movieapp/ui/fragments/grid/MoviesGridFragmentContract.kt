package osc.androiddevacademy.movieapp.ui.fragments.grid

import osc.androiddevacademy.movieapp.model.Movie

interface MoviesGridFragmentContract {

    interface View{
        fun onRequestFail()
        fun onRequestSuccess(movies: ArrayList<Movie>)
        fun onFavouriteMovieAdded()
        fun onFavouriteMovieRemoved()
    }

    interface Presenter{
        fun setView(view: View)
        fun onMoviesRequest()
        fun onFavouriteClicked(movie: Movie)
    }
}