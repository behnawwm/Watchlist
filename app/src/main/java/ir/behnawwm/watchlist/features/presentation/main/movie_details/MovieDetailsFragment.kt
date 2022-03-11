package ir.behnawwm.watchlist.features.presentation.main.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.failure
import ir.behnawwm.watchlist.core.utils.extension.observe
import ir.behnawwm.watchlist.databinding.FragmentMovieDetailsBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(movieDetailsViewModel) {
            observe(movieDetails, ::renderMovieDetails)
            failure(failure, ::handleFailure)
        }
    }
    private fun renderMovieDetails(movie: MovieDetailsView?) {
//        movie?.let {
//            with(movie) {
//                activity?.let {
//                    moviePoster.loadUrlAndPostponeEnterTransition(poster, it)
//                    it.toolbar.title = title
//                }
//                movieSummary.text = summary
//                movieCast.text = cast
//                movieDirector.text = director
//                movieYear.text = year.toString()
//                moviePlay.setOnClickListener { movieDetailsViewModel.playMovie(trailer) }
//            }
//        }
//        movieDetailsAnimator.fadeVisible(scrollView, movieDetails)
//        movieDetailsAnimator.scaleUpView(moviePlay)
    }

    private fun handleFailure(failure: Failure?) {
//        when (failure) {
//            is NetworkConnection -> {
//                notify(R.string.failure_network_connection); close()
//            }
//            is ServerError -> {
//                notify(R.string.failure_server_error); close()
//            }
//            is NonExistentMovie -> {
//                notify(R.string.failure_movie_non_existent); close()
//            }
//            else -> {
//                notify(R.string.failure_server_error); close()
//            }
//        }
    }

}