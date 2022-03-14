package ir.behnawwm.watchlist.features.presentation.main.saved

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mikepenz.fastadapter.items.AbstractItem
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.databinding.ItemMovieBinding
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