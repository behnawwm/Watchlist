package ir.behnawwm.watchlist.features.presentation.main.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.databinding.FragmentSearchBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieFailure
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieListFragmentDirections
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieListItem
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchFragmentViewModel by viewModels()

    private lateinit var searchedMoviesAdapter: FastItemAdapter<SearchMovieListItem>

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
//        loadSavedMoviesList()
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
            rvSearchedMovies.invisible()
            animationViewSearch.visible()
        }
        showProgress()
        viewModel.loadSearchedMovies(query)
    }

    private fun initializeView() {
        initSearchedMoviesList()
        binding.btnSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                query: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {

            }

            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                loadSavedMoviesList(query.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun initSearchedMoviesList() {
        binding.rvSearchedMovies.apply {
            searchedMoviesAdapter = FastItemAdapter()
            layoutManager =
                GridLayoutManager(requireContext(),2)
            adapter = searchedMoviesAdapter
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
            animationViewSearch.invisible()
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
            rvSearchedMovies.invisible()
            animationViewSearch.visible()
        }
        hideProgress()
        Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
//        notifyWithAction(message, R.string.action_refresh, ::loadSavedMoviesList)
    }

}