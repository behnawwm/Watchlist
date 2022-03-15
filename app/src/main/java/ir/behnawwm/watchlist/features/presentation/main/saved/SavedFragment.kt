package ir.behnawwm.watchlist.features.presentation.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.binding.BindingViewHolder
import com.mikepenz.fastadapter.listeners.ClickEventHook
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.core.utils.ui.OptionBottomSheetDialog
import ir.behnawwm.watchlist.databinding.FragmentSavedBinding
import ir.behnawwm.watchlist.databinding.ItemMovieBinding
import ir.behnawwm.watchlist.databinding.ItemSavedMovieBinding
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
            observeEvent(savedMovieStatus, ::renderSavedMovieStatus)
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
        savedMoviesAdapter.addEventHook(object : ClickEventHook<SavedMovieListItem>() {
            override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
                //return the views on which you want to bind this event
                return if (viewHolder is BindingViewHolder<*>) {
                    val binding = viewHolder.binding as ItemSavedMovieBinding
                    binding.ivOptions
                } else {
                    null
                }
            }

            override fun onClick(
                v: View,
                position: Int,
                fastAdapter: FastAdapter<SavedMovieListItem>,
                item: SavedMovieListItem
            ) {
                OptionBottomSheetDialog(
                    requireContext(),
                    listOf(resources.getString(R.string.delete)),
                    listOf(R.drawable.ic_delete),
                    redTintItemPosition = listOf(0)
                ) {
                    when (it) {
                        resources.getString(R.string.delete) -> {
                            viewModel.deleteSavedMovie(savedMoviesAdapter.adapterItems[position].movie)
                            savedMoviesAdapter.remove(position)
                        }
                    }
                }.show()
            }
        })


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

    private fun renderSavedMovieStatus(isInserted: Boolean?) {  //todo CHANGE! not according to Clean
        isInserted?.let {
            if (isInserted)
                notify(R.string.movie_saved_to_watchlist)
            else
                notify(R.string.movie_removed_from_watchlist)
        }
    }

    private fun handleFailure(failure: Failure?) {
//        when (failure) {
//            is MovieFailure.ListNotAvailable -> renderFailure(R.string.failure_movies_list_unavailable)
//            else -> renderFailure(R.string.failure_server_error)
//        }
    }


}