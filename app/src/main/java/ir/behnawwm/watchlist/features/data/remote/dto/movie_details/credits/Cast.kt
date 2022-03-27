package ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.features.presentation.main.movie_details.CastView


@JsonClass(generateAdapter = true)
data class Cast(
    val adult: Boolean?,

    @Json(name = "cast_id")
    val castId: Int?,

    val character: String= "",

    @Json(name = "credit_id")
    val creditId: String = "",

    val gender: Int?,

    val id: Int,

    @Json(name = "known_for_department")
    val knownForDepartment: String?,

    val name: String,

    val order: Int?,

    @Json(name = "original_name")
    val originalName: String = "",

    val popularity: Double?,

    @Json(name = "profile_path")
    val profilePath: String?
) {
    fun toCastView() =
        CastView(id, name, character, profilePath)
}