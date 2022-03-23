package ir.behnawwm.watchlist.features.presentation.main.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.databinding.ItemMovieCastBinding
import ir.behnawwm.watchlist.databinding.ItemMovieGenreBinding
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.Genre
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits.Cast
import ir.behnawwm.watchlist.features.presentation.main.movie_details.CastView

class MovieCastListItem(
    val cast: CastView,
) : AbstractBindingItem<ItemMovieCastBinding>() {
    override val type: Int
        get() = R.id.fastadapter_movie_list

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemMovieCastBinding {
        return ItemMovieCastBinding.inflate(inflater, parent, false)
    }

    override fun bindView(binding: ItemMovieCastBinding, payloads: List<Any>) {
        binding.apply {
            tvNameCast.text = cast.name
            tvCharacterCast.text = cast.character
            ivProfileCast.load(cast.profileImage)
        }
    }

}