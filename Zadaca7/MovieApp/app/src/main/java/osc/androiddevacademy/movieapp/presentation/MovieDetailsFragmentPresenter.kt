package osc.androiddevacademy.movieapp.presentation

import osc.androiddevacademy.movieapp.model.ReviewsResponse
import osc.androiddevacademy.movieapp.networking.interactors.MovieInteractor
import osc.androiddevacademy.movieapp.ui.fragments.details.MovieDetailsFragmentContract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailsFragmentPresenter(private val interactor: MovieInteractor): MovieDetailsFragmentContract.Presenter {

    private lateinit var view: MovieDetailsFragmentContract.View

    override fun setView(view: MovieDetailsFragmentContract.View) {
        this.view = view
    }

    override fun onReviewsRequest(moviesId: Int) {
        interactor.getReviewsForMovie(moviesId, reviewsCallback())
    }

    private fun reviewsCallback(): Callback<ReviewsResponse> = object : Callback<ReviewsResponse> {
        override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
            view.onRequestFailed()
        }

        override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
            if (response.isSuccessful){
                response.body()?.reviews?.run {
                    view.onRequestSuccess(this)
                }
            }
        }
    }
}