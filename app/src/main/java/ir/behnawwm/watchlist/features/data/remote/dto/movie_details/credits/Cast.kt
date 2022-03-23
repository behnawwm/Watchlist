package ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits

import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.features.presentation.main.movie_details.CastView

data class Cast(
    val adult: Boolean,
    val cast_id: Int,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String
) {
    fun toCastView() =
        CastView(id, name, character, GeneralConstants.TMDB_IMAGE_PREFIX_W200 + profile_path)
}