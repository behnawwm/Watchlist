package ir.behnawwm.watchlist.features.presentation.main.movie_list.list_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.utils.extension.loadImage
import ir.behnawwm.watchlist.databinding.ItemMovieBinding
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

class MovieListItem(
    val movie: MovieView,
    val saveListener: (MovieView, Boolean) -> Unit,
) : AbstractBindingItem<ItemMovieBinding>() {
    override val type: Int
        get() = R.id.fastadapter_movie_list

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemMovieBinding {
        return ItemMovieBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMovieBinding, payloads: List<Any>) {
        binding.apply {
            ivMoviePoster.loadImage(movie.poster)
            tvMovieTitle.text = movie.title
            tvRating.text = movie.rating.toString()
            sbSave.isChecked = movie.isSaved
            sbSave.setOnClickListener {
                sbSave.isChecked = !sbSave.isChecked
                saveListener(movie, sbSave.isChecked)
            }
        }
    }

}