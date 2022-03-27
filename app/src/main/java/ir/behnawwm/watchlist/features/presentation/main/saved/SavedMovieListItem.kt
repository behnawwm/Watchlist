package ir.behnawwm.watchlist.features.presentation.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mikepenz.fastadapter.binding.BindingViewHolder
import com.mikepenz.fastadapter.drag.IDraggable
import com.mikepenz.fastadapter.items.AbstractItem
import com.mikepenz.fastadapter.listeners.TouchEventHook
import com.mikepenz.fastadapter.swipe.IDrawerSwipeableViewHolder
import com.mikepenz.fastadapter.swipe.ISwipeable
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.utils.extension.invisible
import ir.behnawwm.watchlist.core.utils.extension.loadImage
import ir.behnawwm.watchlist.core.utils.extension.loadTmdbImageLowQuality
import ir.behnawwm.watchlist.core.utils.extension.visible
import ir.behnawwm.watchlist.core.utils.ui.OptionBottomSheetDialog
import ir.behnawwm.watchlist.databinding.ItemSavedMovieBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

class SavedMovieListItem(
    val movie: MovieView,
    override val isDraggable: Boolean = true,
    override val isSwipeable: Boolean = true,
) : AbstractItem<SavedMovieListItem.ViewHolder>(), IDraggable, ISwipeable {

    override val layoutRes: Int = R.layout.item_saved_movie
    override val type: Int
        get() = R.id.fastadapter_movie_list

    override fun bindView(holder: ViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)

        holder.apply {
            ivMoviePoster.loadTmdbImageLowQuality(movie.poster)
            tvMovieTitle.text = movie.title
            tvRating.text = movie.rating.toString()
        }
    }

    override fun unbindView(holder: ViewHolder) {
        super.unbindView(holder)
        holder.apply {
//            ivMoviePoster.setImageResource(null)
            tvMovieTitle.text = null
            tvRating.text = null
        }
    }

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), IDraggableViewHolder,
        IDrawerSwipeableViewHolder {
        var ivMoviePoster: ImageView = view.findViewById(R.id.ivMoviePoster)
        var tvRating: TextView = view.findViewById(R.id.tvRating)
        var tvMovieTitle: TextView = view.findViewById(R.id.tvMovieTitle)
        var ivOptions: ImageView = view.findViewById(R.id.ivOptions)
        var ivDelete: ImageView = view.findViewById(R.id.ivDelete)
        var ivShare: ImageView = view.findViewById(R.id.ivShare)
        var ivDrag: ImageView = view.findViewById(R.id.ivDrag)
        var layoutItemContent: View = view.findViewById(R.id.layout_item_content)

        override val swipeableView: View
            get() = layoutItemContent

        override fun onDragged() {
            //todo
        }

        override fun onDropped() {
            //todo
        }
    }
}

class DragHandleTouchEvent(val action: (position: Int) -> Unit) :
    TouchEventHook<SavedMovieListItem>() {

    override fun onBind(viewHolder: RecyclerView.ViewHolder): View? {
        return if (viewHolder is SavedMovieListItem.ViewHolder) viewHolder.ivDrag else null
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