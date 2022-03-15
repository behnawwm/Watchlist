package ir.behnawwm.watchlist.features.presentation.main.saved

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mikepenz.fastadapter.binding.BindingViewHolder
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.utils.ui.OptionBottomSheetDialog
import ir.behnawwm.watchlist.databinding.ItemSavedMovieBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

class SavedMovieListItem(
    val movie: MovieView,
) : AbstractBindingItem<ItemSavedMovieBinding>() {
    override val type: Int
        get() = R.id.fastadapter_movie_list

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemSavedMovieBinding {
        return ItemSavedMovieBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemSavedMovieBinding, payloads: List<Any>) {
        binding.apply {
            ivMoviePoster.load(movie.poster)
            tvMovieTitle.text = movie.title
            tvRating.text = movie.rating.toString()
        }
    }

}