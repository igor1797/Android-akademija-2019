package osc.androiddevacademy.movieapp.ui.fragments.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_details.*
import osc.androiddevacademy.movieapp.app.App
import osc.androiddevacademy.movieapp.R
import osc.androiddevacademy.movieapp.common.displayToast
import osc.androiddevacademy.movieapp.ui.adapters.review.ReviewAdapter
import osc.androiddevacademy.movieapp.common.loadImage
import osc.androiddevacademy.movieapp.database.MoviesDatabase
import osc.androiddevacademy.movieapp.model.Movie
import osc.androiddevacademy.movieapp.model.Review
import osc.androiddevacademy.movieapp.networking.BackendFactory
import osc.androiddevacademy.movieapp.presentation.MovieDetailsFragmentPresenter

class MovieDetailsFragment : Fragment(), MovieDetailsFragmentContract.View {
    private val presenter: MovieDetailsFragmentContract.Presenter by lazy { MovieDetailsFragmentPresenter(BackendFactory.getMovieInteractor())}

    private val reviewsAdapter by lazy { ReviewAdapter() }

    private val appDatabase by lazy { MoviesDatabase.getInstance(App.getAppContext()) }
    companion object {

        private const val MOVIE_EXTRA = "movie_extra"
        fun getInstance(movie: Movie): MovieDetailsFragment {
            val fragment =
                MovieDetailsFragment()
            val bundle = Bundle()
            bundle.putParcelable(MOVIE_EXTRA, movie)
            fragment.arguments = bundle
            return fragment
        }

    }
    private lateinit var movie: Movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movie = arguments?.getParcelable(MOVIE_EXTRA) as Movie

        initUi()
        getReviews()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    private fun initUi(){
        movieImage.loadImage(movie.poster)
        movieTitle.text = movie.title
        movieOverview.text = movie.overview
        movieReleaseDate.text = movie.releaseDate
        movieVoteAverage.text = movie.averageVote.toString()

        movieReviewList.apply {
            adapter = reviewsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initListeners(){
        movieFavorite.setOnClickListener {
            onFavoriteClicked()
        }
    }

    private fun onFavoriteClicked(){

    }

    private fun getReviews(){
        presenter.onReviewsRequest(movie.id)
    }

    override fun onRequestSuccess(reviews: List<Review>) {
        reviewsAdapter.setReviewList(reviews)
    }

    override fun onRequestFailed() {
        activity?.displayToast(getString(R.string.failed_toast_text))
    }


}
