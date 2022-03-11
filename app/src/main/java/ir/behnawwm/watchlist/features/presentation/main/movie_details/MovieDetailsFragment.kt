package ir.behnawwm.watchlist.features.presentation.main.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.close
import ir.behnawwm.watchlist.core.utils.extension.failure
import ir.behnawwm.watchlist.core.utils.extension.notify
import ir.behnawwm.watchlist.core.utils.extension.observe
import ir.behnawwm.watchlist.databinding.FragmentMovieDetailsBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieFailure

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

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

        with(viewModel) {
            observe(movieDetails, ::renderMovieDetails)
            failure(failure, ::handleFailure)
        }
        viewModel.loadMovieDetails(args.selectedMovieId)
    }

    private fun renderMovieDetails(movie: MovieDetailsView?) {
        binding.apply {
            movie?.let {
                with(movie) {
                    moviePoster.load(poster)
                    movieSummary.text = summary
                    movieCast.text = cast
                    movieDirector.text = director
                    movieYear.text = year.toString()
//                    moviePlay.setOnClickListener { movieDetailsViewModel.playMovie(trailer) }
                }
            }
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> {
                notify(R.string.failure_network_connection); close()
            }
            is Failure.ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is MovieFailure.NonExistentMovie -> {
                notify(R.string.failure_movie_non_existent); close()
            }
            else -> {
                notify(R.string.failure_server_error); close()
            }
        }
    }

}