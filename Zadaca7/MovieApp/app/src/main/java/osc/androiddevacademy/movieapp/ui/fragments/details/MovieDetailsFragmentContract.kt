package osc.androiddevacademy.movieapp.ui.fragments.details

import osc.androiddevacademy.movieapp.model.Review

interface MovieDetailsFragmentContract {

    interface View{
        fun onRequestFailed()
        fun onRequestSuccess(reviews: List<Review>)
    }

    interface Presenter{
        fun setView(view: View)
        fun onReviewsRequest(moviesId: Int)
    }
}