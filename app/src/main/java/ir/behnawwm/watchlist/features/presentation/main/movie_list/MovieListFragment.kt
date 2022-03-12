package ir.behnawwm.watchlist.features.presentation.main.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.databinding.FragmentMovieListBinding

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: MovieListViewModel by viewModels()

    private lateinit var popularMoviesAdapter: FastItemAdapter<MovieListItem>
    private lateinit var topRatedMoviesAdapter: FastItemAdapter<MovieListItem>

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
            observe(popularMovies, ::renderPopularMoviesList)
            observe(topRatedMovies, ::renderTopRatedMoviesList)
            failure(failure, ::handleFailure)
        }

    }

    private fun initializeView() {
        initializePopularList()
        initializeTopRatedList()
    }

    private fun initializePopularList() {
        binding.rvPopularMovies.apply {
            popularMoviesAdapter = FastItemAdapter()
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            adapter = popularMoviesAdapter
            popularMoviesAdapter.onClickListener = { view, adapter, item, position ->
                val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(item.movie.id)
                findNavController().navigate(action)
                true
            }
        }
    }
    private fun initializeTopRatedList() {
        binding.rvTopRatedMovies.apply {
            topRatedMoviesAdapter = FastItemAdapter()
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)
            adapter = topRatedMoviesAdapter
            topRatedMoviesAdapter.onClickListener = { view, adapter, item, position ->
                val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(item.movie.id)
                findNavController().navigate(action)
                true
            }
        }
    }

    private fun loadMoviesList() {
        binding.apply {
            ivEmpty.invisible()
            rvPopularMovies.visible()
            rvTopRatedMovies.visible()
        }
        showProgress()
        viewModel.loadPopularMovies()
        viewModel.loadTopRatedMovies()
    }

    private fun renderPopularMoviesList(movies: List<MovieView>?) {
        popularMoviesAdapter.set(movies.orEmpty().map { it.toMovieListItem() })
        hideProgress()
    }
    private fun renderTopRatedMoviesList(movies: List<MovieView>?) {
        topRatedMoviesAdapter.set(movies.orEmpty().map { it.toMovieListItem() })
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is MovieFailure.ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        binding.apply {
            rvPopularMovies.invisible()
            rvTopRatedMovies.invisible()
            ivEmpty.visible()
        }
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}