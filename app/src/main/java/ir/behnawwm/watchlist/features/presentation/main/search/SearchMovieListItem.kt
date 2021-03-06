package ir.behnawwm.watchlist.features.presentation.main.search

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
import ir.behnawwm.watchlist.core.utils.extension.loadImage
import ir.behnawwm.watchlist.core.utils.extension.loadTmdbImageLowQuality
import ir.behnawwm.watchlist.databinding.ItemMovieBinding
import ir.behnawwm.watchlist.databinding.ItemSavedMovieBinding
import ir.behnawwm.watchlist.databinding.ItemSearchedMovieBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

class SearchMovieListItem(
    val movie: MovieView,
) : AbstractBindingItem<ItemSearchedMovieBinding>() {
    override val type: Int
        get() = R.id.fastadapter_movie_list

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemSearchedMovieBinding {
        return ItemSearchedMovieBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemSearchedMovieBinding, payloads: List<Any>) {
        binding.apply {
            ivMoviePoster.loadTmdbImageLowQuality(movie.poster)
            tvMovieTitle.text = movie.title
            tvRating.text = movie.rating.toString()
        }
    }

}