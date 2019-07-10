package osc.androiddevacademy.movieapp.ui.fragments.favourite

import osc.androiddevacademy.movieapp.model.Movie

interface MoviesFavouriteFragmentContract {

    interface View{
        fun onMoviesReceived(movies: List<Movie>)
        fun onEmptyListReceived()
        fun onFavouriteRemoved(movie: Movie)
    }

    interface Presenter{
        fun setView(view: View)
        fun onRequestFavouriteMovies()
        fun onFavouriteClicked(movie: Movie)
    }
}