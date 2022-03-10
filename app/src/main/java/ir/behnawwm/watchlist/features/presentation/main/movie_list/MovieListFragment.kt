package ir.behnawwm.watchlist.features.presentation.main.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.core.utils.extension.failure
import ir.behnawwm.watchlist.core.utils.extension.observe
import ir.behnawwm.watchlist.databinding.FragmentMovieListBinding
import javax.inject.Inject

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private lateinit var binding: FragmentMovieListBinding
    private val viewModel : MovieListViewModel by viewModels()

//    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(layoutInflater)

//        with(viewModel) {
//            observe(movies, ::renderMoviesList)
//            failure(failure, ::handleFailure)
//        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initializeView()
//        loadMoviesList()
    }

//    private fun initializeView() {
//        movieList.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//        movieList.adapter = moviesAdapter
//        moviesAdapter.clickListener = { movie, navigationExtras ->
//            navigator.showMovieDetails(requireActivity(), movie, navigationExtras)
//        }
//    }
//
//    private fun loadMoviesList() {
//        emptyView.invisible()
//        movieList.visible()
//        showProgress()
//        moviesViewModel.loadMovies()
//    }
//
//    private fun renderMoviesList(movies: List<MovieView>?) {
//        moviesAdapter.collection = movies.orEmpty()
//        hideProgress()
//    }
//
//    private fun handleFailure(failure: Failure?) {
//        when (failure) {
//            is NetworkConnection -> renderFailure(R.string.failure_network_connection)
//            is ServerError -> renderFailure(R.string.failure_server_error)
//            is ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
//            else -> renderFailure(R.string.failure_server_error)
//        }
//    }
//
//    private fun renderFailure(@StringRes message: Int) {
//        movieList.invisible()
//        emptyView.visible()
//        hideProgress()
//        notifyWithAction(message, R.string.action_refresh, ::loadMoviesList)
//    }
}