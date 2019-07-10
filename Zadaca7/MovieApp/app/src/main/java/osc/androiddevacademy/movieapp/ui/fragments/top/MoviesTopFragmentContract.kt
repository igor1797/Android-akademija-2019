package osc.androiddevacademy.movieapp.ui.fragments.top

import osc.androiddevacademy.movieapp.model.Movie

interface MoviesTopFragmentContract {

    interface View{
        fun onRequestSuccess(movies: ArrayList<Movie>)
        fun onRequestFailed()
        fun onAddFavourite()
        fun onRemoveFavourite()
    }

    interface Presenter{
        fun setView(view: View)
        fun onMovieRequest()
        fun onFavouriteClicked(movie: Movie)
    }
}