package ir.behnawwm.watchlist.features.presentation.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.databinding.FragmentSavedBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieFailure
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

@AndroidEntryPoint
class SavedFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private val viewModel: SavedFragmentViewModel by viewModels()

    private lateinit var savedMoviesAdapter: FastItemAdapter<SavedMovieListItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
        loadSavedMoviesList()
        initObservers()

    }

    private fun initObservers() {
        with(viewModel) {
            observe(savedMovies, ::renderSavedMovies)
            failure(failure, ::handleFailure)
        }
    }

    private fun loadSavedMoviesList() {
        binding.apply {
            rvSavedMovies.invisible()
            tvTitleSaved.invisible()
        }
        showProgress()
        viewModel.loadSavedMovies()
    }

    private fun initializeView() {
        initSavedMoviesList()
    }

    private fun initSavedMoviesList() {
        savedMoviesAdapter = FastItemAdapter()
        binding.rvSavedMovies.apply {
            adapter = savedMoviesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun renderSavedMovies(savedMovies: List<MovieView>?) {
        savedMoviesAdapter.set(savedMovies.orEmpty().map { it.toSavedMovieListItem() })
        hideProgress()
        binding.apply {
            rvSavedMovies.visible()
            tvTitleSaved.visible()
        }
    }

    private fun handleFailure(failure: Failure?) {
//        when (failure) {
//            is MovieFailure.ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
//            else -> renderFailure(R.string.failure_server_error)
//        }
    }



}