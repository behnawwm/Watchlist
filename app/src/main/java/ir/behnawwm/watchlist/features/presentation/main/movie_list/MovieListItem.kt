package ir.behnawwm.watchlist.features.presentation.main.movie_list

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

class MovieListItem(
    val movie: MovieView
) : AbstractBindingItem<ItemMovieBinding>() {
    override val type: Int
        get() = R.id.fastadapter_movie_list

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemMovieBinding {
        return ItemMovieBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMovieBinding, payloads: List<Any>) {
        binding.apply {
            ivMoviePoster.load(movie.poster)
        }
    }

}