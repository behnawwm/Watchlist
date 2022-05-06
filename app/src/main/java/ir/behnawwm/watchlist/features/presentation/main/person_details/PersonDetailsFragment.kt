package ir.behnawwm.watchlist.features.presentation.main.person_details

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.varunest.sparkbutton.SparkButton
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.databinding.FragmentMovieDetailsBinding
import ir.behnawwm.watchlist.databinding.FragmentPersonDetailsBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_details.list_item.MovieCastListItem
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieFailure
import ir.behnawwm.watchlist.features.presentation.main.movie_details.list_item.MovieGenreListItem


@AndroidEntryPoint
class PersonDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPersonDetailsBinding
    private val viewModel: PersonDetailsViewModel by viewModels()
    private val args: PersonDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
        with(viewModel) {
            observe(personDetails, ::renderPersonDetails)
            failureEvent(failure, ::handleFailure)
        }
        showProgress()
        viewModel.loadMovieDetails(args.selectedMovieId)
    }

    private fun initializeView() {

    }

    private fun renderPersonDetails(personDetails: PersonDetailsView?) {
        binding.apply {
            personDetails?.let {
                with(personDetails) {
                    ivPoster.loadTmdbImageLowQuality(profilePath)
                    ivPosterBackdrop.loadTmdbImageOriginalQuality(profilePath)
                    tvMovieTitle.text = name
                    tvDescription.text = biography
//                    tvRating.text = rating.toString()
                }
            }
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