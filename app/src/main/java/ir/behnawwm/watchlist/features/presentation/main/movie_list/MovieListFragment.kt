package ir.behnawwm.watchlist.features.presentation.main.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.databinding.FragmentMovieListBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.list_item.MovieListItem


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
        loadSavedMoviesList()
        initObservers()
    }

    private fun initializeView() {
        initializePopularList()
        initializeTopRatedList()
        initializeSwipeRefreshLayout()
    }

    private fun initializeSwipeRefreshLayout() {
        binding.layoutSwipeRefresh.setOnRefreshListener {
            loadSavedMoviesList()
            binding.layoutSwipeRefresh.isRefreshing = false
        }
    }

    private fun initObservers() {
        with(viewModel) {
            observeEvent(popularMovies, ::renderPopularMoviesList)
            observeEvent(topRatedMovies, ::renderTopRatedMoviesList)
            observeEvent(savedMovieStatus, ::renderSavedMovieStatus)
            failureEvent(failure, ::handleFailure)
        }
    }

    private fun loadSavedMoviesList() {
        hideAllViews()
        showProgress()
        loadAllMoviesLists()
    }

    private fun initializePopularList() {
        binding.rvPopularMovies.apply {
            popularMoviesAdapter = FastItemAdapter()
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            popularMoviesAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            adapter = popularMoviesAdapter
            popularMoviesAdapter.onClickListener = { view, adapter, item, position ->
                val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(
                        item.movie.id,
                        item.movie.isSaved
                    )
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
            topRatedMoviesAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

            adapter = topRatedMoviesAdapter
            topRatedMoviesAdapter.onClickListener = { view, adapter, item, position ->
                val action =
                    MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(
                        item.movie.id,
                        item.movie.isSaved
                    )
                findNavController().navigate(action)
                true
            }
        }
    }

    private fun loadAllMoviesLists() {
        viewModel.loadSavedMoviesList()
        viewModel.loadPopularMovies()
        viewModel.loadTopRatedMovies()
    }

    private fun renderPopularMoviesList(movies: List<MovieView>?) {
        popularMoviesAdapter.set(movies.orEmpty().map {
            it.toMovieListItem() { movie, b ->
                if (b)
                    viewModel.insertSavedMovie(movie)
                else
                    viewModel.deleteSavedMovie(movie)
            }
        })
        showAllViews()
        hideProgress()
    }

    private fun renderTopRatedMoviesList(movies: List<MovieView>?) {
        topRatedMoviesAdapter.set(movies.orEmpty().map {
            it.toMovieListItem() { movie, b ->
                if (b)
                    viewModel.insertSavedMovie(movie)
                else
                    viewModel.deleteSavedMovie(movie)
            }
        })
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
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is MovieFailure.ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        hideAllViews()
        showErrorViews(message)

        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadSavedMoviesList)
    }

    private fun showErrorViews(@StringRes message: Int) {
        binding.apply {
            showAnimationView()
            tvError.text = resources.getString(message)
        }
    }

    private fun showAnimationView() {
        binding.animationViewError.apply {
            visible()
            progress = 0f
            playAnimation()
        }
        doWithDelay(binding.animationViewError.duration - 200) {
            lifecycleScope.launchWhenResumed {
                binding.tvError.showWithFade()
            }
        }
    }

    private fun hideAllViews() {
        binding.apply {
//            ivEmpty.invisible()
            rvPopularMovies.gone()
            rvTopRatedMovies.gone()
            tvPopular.gone()
            tvTopRated.gone()
            tvMorePopular.gone()
            tvMoreTopRated.gone()
            animationViewError.gone()
            tvError.invisible()
        }
    }

    private fun showAllViews() {
        binding.apply {
//            ivEmpty.invisible()
            rvPopularMovies.visible()
            rvTopRatedMovies.visible()
            tvPopular.visible()
            tvTopRated.visible()
            tvMorePopular.visible()
            tvMoreTopRated.visible()
        }
    }
}