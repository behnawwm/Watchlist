package ir.behnawwm.watchlist.features.presentation.main.movie_details.list_item

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.constants.GeneralConstants.TMDB_IMAGE_PREFIX_W200
import ir.behnawwm.watchlist.core.utils.extension.loadImage
import ir.behnawwm.watchlist.core.utils.extension.loadTmdbImageLowQuality
import ir.behnawwm.watchlist.databinding.ItemMovieCastBinding
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
            ivProfileCast.loadTmdbImageLowQuality(cast.profileImage)
        }
    }

}