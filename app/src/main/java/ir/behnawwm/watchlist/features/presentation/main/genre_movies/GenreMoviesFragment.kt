package ir.behnawwm.watchlist.features.presentation.main.genre_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.observe
import ir.behnawwm.watchlist.core.utils.extension.failureEvent
import ir.behnawwm.watchlist.core.utils.extension.hideProgress
import ir.behnawwm.watchlist.core.utils.extension.notify
import ir.behnawwm.watchlist.core.utils.extension.showProgress
import ir.behnawwm.watchlist.databinding.FragmentGenreMoviesBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView
import ir.behnawwm.watchlist.features.presentation.main.search.SearchMovieListItem

@AndroidEntryPoint
class GenreMoviesFragment : Fragment() {

    private var _binding: FragmentGenreMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GenreMoviesViewModel by viewModels()
    private val args: GenreMoviesFragmentArgs by navArgs()

    private lateinit var moviesAdapter: FastItemAdapter<SearchMovieListItem>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenreMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerView()
        binding.tvGenreName.text = args.genreName

        with(viewModel) {
            observe(movies, ::renderMovies)
            failureEvent(failure, ::handleFailure)
        }
        showProgress()
        viewModel.loadMovies(args.genreId)
    }

    private fun initializeRecyclerView() {
        binding.rvGenreMovies.apply {
            moviesAdapter = FastItemAdapter()
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = moviesAdapter
            moviesAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            moviesAdapter.onClickListener = { _, _, item, _ ->
                val action =
                    GenreMoviesFragmentDirections.actionGenreMoviesFragmentToMovieDetailsFragment(
                        item.movie.id,
                        item.movie.isSaved
                    )
                findNavController().navigate(action)
                true
            }
        }
    }

    private fun renderMovies(movies: List<MovieView>?) {
        moviesAdapter.set(movies.orEmpty().map { SearchMovieListItem(it) })
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> notify(ir.behnawwm.watchlist.R.string.failure_network_connection)
            is Failure.ServerError -> notify(ir.behnawwm.watchlist.R.string.failure_server_error)
            else -> notify(ir.behnawwm.watchlist.R.string.failure_server_error)
        }
        hideProgress()
    }
}
