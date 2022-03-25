package ir.behnawwm.watchlist.features.presentation.main.movie_details.list_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.databinding.ItemMovieGenreBinding
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.Genre

class MovieGenreListItem(
    val genre: Genre,
) : AbstractBindingItem<ItemMovieGenreBinding>() {
    override val type: Int
        get() = R.id.fastadapter_movie_list

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemMovieGenreBinding {
        return ItemMovieGenreBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMovieGenreBinding, payloads: List<Any>) {
        binding.apply {
           tvTitleGenre.text = genre.name
        }
    }

}