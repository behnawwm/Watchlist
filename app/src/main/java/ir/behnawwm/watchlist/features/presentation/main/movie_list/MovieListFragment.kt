package ir.behnawwm.watchlist.features.presentation.main.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.functional.Event
import ir.behnawwm.watchlist.core.functional.EventObserver
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
        initObservers()

    }

    private fun initObservers() {
        with(viewModel) {
            observe(popularMovies, ::renderPopularMoviesList)
            observe(topRatedMovies, ::renderTopRatedMoviesList)
//            observeEvent(savedMovieStatus, ::renderSavedMovieStatus)  todo make extension
            failure(failure, ::handleFailure)
        }
        viewModel.savedMovieStatus.observe(viewLifecycleOwner, EventObserver {
            renderSavedMovieStatus(it)
        })
    }

    private fun initializeView() {
        initializePopularList()
        initializeTopRatedList()
    }

    private fun initializePopularList() {
        binding.rvPopularMovies.apply {
            popularMoviesAdapter = FastItemAdapter()
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = topRatedMoviesAdapter
            topRatedMoviesAdapter.onClickListener = { view, adapter, item, position ->
                val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(item.movie.id)
                findNavController().navigate(action)
                true
            }
//            topRatedMoviesAdapter.addEventHook(object : ClickEventHook<MovieListItem>() {
//
//                override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
//                    return AbstractBindingItem
//                }
//
//                override fun onClick(v: View, position: Int, fastAdapter: FastAdapter<MovieListItem>, item: MovieListItem) {
//                    view
//                }
//            })
        }
    }

    private fun loadMoviesList() {
        binding.apply {
//            ivEmpty.invisible()
            rvPopularMovies.invisible()
            rvTopRatedMovies.invisible()
            tvPopular.invisible()
            tvTopRated.invisible()
        }
        showProgress()
        viewModel.loadPopularMovies()
        viewModel.loadTopRatedMovies()
    }

    private fun renderPopularMoviesList(movies: List<MovieView>?) {
        popularMoviesAdapter.set(movies.orEmpty().map {
            it.toMovieListItem() { movie, b ->
                if (b)
                    viewModel.insertSavedMovie(movie)
                else
                    viewModel.removeSavedMovie(movie)
            }
        })
        binding.apply {
//            ivEmpty.invisible()
            rvPopularMovies.visible()
            rvTopRatedMovies.visible()
            tvPopular.visible()
            tvTopRated.visible()
        }
        hideProgress()
    }

    private fun renderTopRatedMoviesList(movies: List<MovieView>?) {
        topRatedMoviesAdapter.set(movies.orEmpty().map {
            it.toMovieListItem() { movie, b ->
                if (b)
                    viewModel.insertSavedMovie(movie)
                else
                    viewModel.removeSavedMovie(movie)
            }
        })
        hideProgress()
    }

    private fun renderSavedMovieStatus(isInserted: Boolean) {  //todo CHANGE! not according to CleanCoding patterns
        if (isInserted)
            notifyWithAction(R.string.movie_saved_to_watchlist, R.string.view) {
                findNavController().navigate(R.id.action_movieListFragment_to_savedFragment)
            }
        else
            notifyWithAction(R.string.movie_removed_from_watchlist, R.string.view) {
                findNavController().navigate(R.id.action_movieListFragment_to_savedFragment)
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

    private fun renderFailure(@StringRes message: Int) {
        binding.apply {
            rvPopularMovies.invisible()
            rvTopRatedMovies.invisible()
//            ivEmpty.visible()
        }
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
    }
}