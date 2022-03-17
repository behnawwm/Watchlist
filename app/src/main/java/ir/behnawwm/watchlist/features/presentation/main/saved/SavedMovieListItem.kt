package ir.behnawwm.watchlist.features.presentation.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mikepenz.fastadapter.binding.BindingViewHolder
import com.mikepenz.fastadapter.drag.IDraggable
import com.mikepenz.fastadapter.listeners.TouchEventHook
import com.mikepenz.fastadapter.swipe.ISwipeable
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.utils.extension.invisible
import ir.behnawwm.watchlist.core.utils.extension.visible
import ir.behnawwm.watchlist.core.utils.ui.OptionBottomSheetDialog
import ir.behnawwm.watchlist.databinding.ItemSavedMovieBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

class SavedMovieListItem(
    val movie: MovieView,
    override val isDraggable: Boolean = true,
    override val isSwipeable: Boolean = true,   //todo apply swipe effects
) : AbstractBindingItem<ItemSavedMovieBinding>(), IDraggable, ISwipeable {
    override val type: Int
        get() = R.id.fastadapter_movie_list

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ItemSavedMovieBinding {
        return ItemSavedMovieBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemSavedMovieBinding, payloads: List<Any>) {
        binding.apply {
            ivMoviePoster.load(movie.poster)
            tvMovieTitle.text = movie.title
            tvRating.text = movie.rating.toString()

            val p = payloads.mapNotNull { it as? Bundle }.lastOrNull()
            p?.let {
                if (it.getBoolean(IS_DRAG_ENABLED)) {
                    ivDrag.visible()
                    ivOptions.invisible()
                } else {
                    ivDrag.invisible()
                    ivOptions.visible()
                }
            }
        }
    }

    companion object {
        const val IS_DRAG_ENABLED = "IS_DRAG_ENABLED"
    }

}

class DragHandleTouchEvent(val action: (position: Int) -> Unit) :
    TouchEventHook<SavedMovieListItem>() {

    override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
        return if (viewHolder is BindingViewHolder<*>) {
            val binding = viewHolder.binding as ItemSavedMovieBinding
            binding.ivDrag
        } else {
            null
        }
    }

    override fun onTouch(
        v: View,
        event: MotionEvent,
        position: Int,
        fastAdapter: FastAdapter<SavedMovieListItem>,
        item: SavedMovieListItem
    ): Boolean {
        return if (event.action == MotionEvent.ACTION_DOWN) {
            action(position)
            true
        } else {
            false
        }
    }

}