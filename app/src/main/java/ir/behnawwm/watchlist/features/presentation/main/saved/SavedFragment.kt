package ir.behnawwm.watchlist.features.presentation.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.drag.ItemTouchCallback
import com.mikepenz.fastadapter.drag.SimpleDragCallback
import com.mikepenz.fastadapter.listeners.addClickListener
import com.mikepenz.fastadapter.swipe.SimpleSwipeDrawerCallback
import com.mikepenz.fastadapter.swipe_drag.SimpleSwipeDrawerDragCallback
import com.mikepenz.fastadapter.utils.DragDropUtil
import dagger.hilt.android.AndroidEntryPoint
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.utils.extension.*
import ir.behnawwm.watchlist.core.utils.ui.OptionBottomSheetDialog
import ir.behnawwm.watchlist.databinding.FragmentSavedBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

@AndroidEntryPoint
class SavedFragment : Fragment(), ItemTouchCallback, SimpleSwipeDrawerCallback.ItemSwipeCallback {
    private lateinit var binding: FragmentSavedBinding
    private val viewModel: SavedFragmentViewModel by viewModels()

    private lateinit var savedMoviesAdapter: FastItemAdapter<SavedMovieListItem>
    private lateinit var touchCallback: SimpleDragCallback
    private lateinit var touchHelper: ItemTouchHelper

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
            failureEvent(failure, ::handleFailure)
        }
    }

    private fun loadSavedMoviesList() {
        binding.apply {
            rvSavedMovies.gone()
            tvTitleSaved.gone()
        }
        showProgress()
        viewModel.loadSavedMovies()
    }

    private fun initializeView() {
        initSavedMoviesList()
    }

    private fun initSavedMoviesList() {
        savedMoviesAdapter = FastItemAdapter()

        touchCallback = SimpleSwipeDrawerDragCallback(
            this,
            ItemTouchHelper.LEFT,
            this
        )
            .withNotifyAllDrops(true)   //todo change
            .withSwipeLeft(80) // Width of delete button
//            .withSwipeRight(160) // Width of archive and share buttons
            .withSensitivity(10f)
            .withSurfaceThreshold(0.3f)
        touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(binding.rvSavedMovies)

        savedMoviesAdapter.addClickListener<SavedMovieListItem.ViewHolder, SavedMovieListItem>(
            resolveView = { viewHolder ->
                viewHolder.ivOptions
            }, onClick = { view, position, adapter, item ->
                showOptionsDialog(item, position)
            })
        savedMoviesAdapter.addClickListener<SavedMovieListItem.ViewHolder, SavedMovieListItem>(
            resolveView = { viewHolder ->
                viewHolder.ivDelete
            }, onClick = { view, position, adapter, item ->
                deleteMovie(item, position)
            })
        savedMoviesAdapter.addClickListener<SavedMovieListItem.ViewHolder, SavedMovieListItem>(
            resolveView = { viewHolder ->
                viewHolder.ivShare
            }, onClick = { view, position, adapter, item ->
                shareMovie(item, position)
            })

        // Add an event hook that manages touching the drag handle
        savedMoviesAdapter.addEventHook(
            DragHandleTouchEvent { position ->
                binding.rvSavedMovies.findViewHolderForAdapterPosition(position)
                    ?.let { viewHolder ->
                        touchHelper.startDrag(viewHolder)
                    }
            }
        )
        savedMoviesAdapter.onClickListener = { view, adapter, item, position ->
            val action =
                SavedFragmentDirections.actionSavedFragmentToMovieDetailsFragment(item.movie.id,item.movie.isSaved)
            findNavController().navigate(action)
            true
        }

        savedMoviesAdapter.onLongClickListener = { view, adapter, item, position ->
            true
        }

        binding.rvSavedMovies.apply {
            adapter = savedMoviesAdapter
            layoutManager = LinearLayoutManager(requireContext())
            savedMoviesAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private fun deleteMovie(item: SavedMovieListItem, position: Int) {
        viewModel.deleteSavedMovie(item.movie)
        savedMoviesAdapter.remove(position)
    }

    private fun shareMovie(item: SavedMovieListItem, position: Int) {
        //todo Feature share!
        Toast.makeText(requireContext(), "Not Implemented!", Toast.LENGTH_SHORT).show()
    }

    private fun showOptionsDialog(item: SavedMovieListItem, position: Int) {
        OptionBottomSheetDialog(
            requireContext(),
            listOf(resources.getString(R.string.delete)),
            listOf(R.drawable.ic_delete),
            redTintItemPosition = listOf(0)
        ) {
            when (it) {
                resources.getString(R.string.delete) -> {
                    deleteMovie(item,position)
                }
            }
        }.show()
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

    override fun itemTouchOnMove(oldPosition: Int, newPosition: Int): Boolean {
        DragDropUtil.onMove(
            savedMoviesAdapter.itemAdapter,
            oldPosition,
            newPosition
        ) // change position
        return true
    }

    override fun itemTouchDropped(oldPosition: Int, newPosition: Int) {
        super.itemTouchDropped(oldPosition, newPosition)
        //todo save positions in db
    }

    override fun itemSwiped(position: Int, direction: Int) {
        //todo
    }

    override fun itemUnswiped(position: Int) {
        //todo
    }
}