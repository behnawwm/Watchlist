package ir.behnawwm.watchlist.features.presentation.main.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.databinding.FragmentMovieDetailsBinding
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits.MovieCredits
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieCastListItem
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieFailure
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieGenreListItem
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieListFragmentDirections

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()

    private lateinit var genresAdapter: FastItemAdapter<MovieGenreListItem>
    private lateinit var castAdapter: FastItemAdapter<MovieCastListItem>

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

        initializeView()
        with(viewModel) {
            observe(movieDetails, ::renderMovieDetails)
            observe(movieCredits, ::renderMovieCredits)
            failureEvent(failure, ::handleFailure)
        }
        showProgress()
        viewModel.loadAll(args.selectedMovieId)
    }

    private fun initializeView() {
        initializeGenresList()
        initializeCastList()
        binding.apply {
            btnBack.setOnClickListener {
                close()
            }
            btnSave.setOnClickListener {
                //todo
            }
        }
    }

    private fun initializeCastList() {
        binding.rvCast.apply {
            castAdapter = FastItemAdapter()
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
            castAdapter.onClickListener = { view, adapter, item, position ->
                //todo navigate to movies with selected cast
                true
            }
        }
    }

    private fun initializeGenresList() {
        binding.rvGenres.apply {
            genresAdapter = FastItemAdapter()
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = genresAdapter
            genresAdapter.onClickListener = { view, adapter, item, position ->
                //todo navigate to movies with selected genre
                true
            }
        }
    }

    private fun renderMovieDetails(movieDetails: MovieDetailsView?) {
        binding.apply {
            movieDetails?.let {
                with(movieDetails) {
                    ivPoster.load(poster)
                    tvMovieTitle.text = title
                    tvDescription.text = summary
                    tvRating.text = rating.toString()

                    genresAdapter.set(movieDetails.genres.map { MovieGenreListItem(it) })
                }
            }
        }
        hideProgress()
    }

    private fun renderMovieCredits(movieCredits: MovieCreditsView?) {
        movieCredits?.let {
            castAdapter.set(movieCredits.cast.map { MovieCastListItem(it) })
        }
        hideProgress()
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
        hideProgress()
    }

}