package ir.behnawwm.watchlist.features.presentation.main.movie_details

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.varunest.sparkbutton.SparkButton
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.databinding.FragmentMovieDetailsBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_details.list_item.MovieCastListItem
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieFailure
import ir.behnawwm.watchlist.features.presentation.main.movie_details.list_item.MovieGenreListItem


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
            observeEvent(savedMovieStatus, ::renderSavedMovieStatus)
            failureEvent(failure, ::handleFailure)
        }
        showProgress()
        viewModel.loadAll(args.selectedMovieId)
    }

    private fun initializeView() {
        initializeToolbar()
        initializeGenresList()
        initializeCastList()
        binding.apply {
//            btnBack.setOnClickListener {
//                close()
//            }
//            btnSave.setOnClickListener {
//                //todo
//            }
        }
    }

    private fun initializeToolbar() {
        with(requireActivity() as AppCompatActivity) {
            setupActionBar(binding.detailsToolbar) {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
                setHasOptionsMenu(true)
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
                    ivPoster.loadImage(poster)
                    ivPosterBackdrop.loadImage(poster)
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

    private fun renderSavedMovieStatus(isInserted: Boolean?) {  //todo CHANGE! not according to Clean
        isInserted?.let {
            if (isInserted)
//                notifyWithAction(R.string.movie_saved_to_watchlist, R.string.view) {  //todo check why couldn't navigate back
//                    findNavController().navigate(R.id.action_global_savedFragment)
//                }
                notify(R.string.movie_saved_to_watchlist)
            else
//                notifyWithAction(R.string.movie_removed_from_watchlist, R.string.view) {
//                    findNavController().navigate(R.id.action_global_savedFragment)
//                }
                notify(R.string.movie_removed_from_watchlist)

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
        hideProgress()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)

        val actionView = menu.findItem(R.id.menu_save).actionView as SparkButton
        actionView.isChecked = args.isSaved

        actionView.setOnClickListener {
            actionView.isChecked = !actionView.isChecked
            if (actionView.isChecked)
                viewModel.insertSavedMovie(viewModel.movieDetails.value!!.toMovieView())
            else
                viewModel.deleteSavedMovie(viewModel.movieDetails.value!!.toMovieView())
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    var flag = true
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
            R.id.menu_save -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

}