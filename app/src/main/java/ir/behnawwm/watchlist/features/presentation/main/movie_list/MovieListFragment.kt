package ir.behnawwm.watchlist.features.presentation.main.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.failure
import ir.behnawwm.watchlist.core.utils.extension.observe
import ir.behnawwm.watchlist.databinding.FragmentMovieListBinding

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var moviesAdapter: FastItemAdapter<MovieListItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
        loadMoviesList()

        with(viewModel) {
            observe(movies, ::renderMoviesList)
            failure(failure, ::handleFailure)
        }

    }

    private fun initializeView() {
        binding.rvMovies.apply {
            moviesAdapter = FastItemAdapter()
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = moviesAdapter
            //todo click listener
        }
    }

    private fun loadMoviesList() {
//        emptyView.invisible()
//        movieList.visible()
//        showProgress()
        viewModel.loadMovies()
    }

    private fun renderMoviesList(movies: List<MovieView>?) {
        moviesAdapter.set(movies.orEmpty().map { it.toMovieListItem() })
//        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
//            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
//            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
//            is MovieFailure.ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
//            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
//        movieList.invisible()
//        emptyView.visible()
//        hideProgress()
//        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}