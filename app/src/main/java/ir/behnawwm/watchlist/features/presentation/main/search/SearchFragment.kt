package ir.behnawwm.watchlist.features.presentation.main.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.constants.GeneralConstants.SEARCH_DEBOUNCE_DELAY_TIME
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.databinding.FragmentSearchBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieFailure
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchFragmentViewModel by viewModels()

    private lateinit var searchedMoviesAdapter: FastItemAdapter<SearchMovieListItem>
    private var searchJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
        initObservers()
    }

    private fun initObservers() {
        with(viewModel) {
            observe(searchedMovies, ::renderSearchedMovies)
            failureEvent(failure, ::handleFailure)
        }
    }

    private fun loadSavedMoviesList(query: String) {
        binding.apply {
            rvSearchedMovies.gone()
            animationViewSearch.visible()
        }
        showProgress()
        viewModel.loadSearchedMovies(query)
    }

    private fun initializeView() {
        initSearchedMoviesList()
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                query: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                searchDebounced(query.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }
    fun searchDebounced(searchText: String) {   //todo should it be moved to viewmodel ?
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_TIME)
            loadSavedMoviesList(searchText)
        }
    }

    private fun initSearchedMoviesList() {
        binding.rvSearchedMovies.apply {
            searchedMoviesAdapter = FastItemAdapter()
            layoutManager =
                GridLayoutManager(requireContext(),2)
            adapter = searchedMoviesAdapter
            searchedMoviesAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            searchedMoviesAdapter.onClickListener = { view, adapter, item, position ->
                val action =
                    SearchFragmentDirections.actionSearchFragmentToMovieDetailsFragment(item.movie.id)
                findNavController().navigate(action)
                true
            }
        }
    }
    private fun renderSearchedMovies(movies: List<MovieView>?) {
        searchedMoviesAdapter.set(movies.orEmpty().map {
            SearchMovieListItem(it)
        })
        showAllViews()
        hideProgress()
    }

    private fun showAllViews() {
        binding.apply {
            rvSearchedMovies.visible()
            animationViewSearch.gone()
        }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is MovieFailure.ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) { //todo
        binding.apply {
            rvSearchedMovies.gone()
            animationViewSearch.visible()
        }
        hideProgress()
//        notifyWithAction(message, R.string.action_refresh, ::loadSavedMoviesList)
    }

}