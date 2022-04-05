package ir.behnawwm.watchlist.features.data.remote.dto.movie_details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguage(
    @Json(name = "english_name")
    val englishName: String?,

    @Json(name = "iso_639_1")
    val iso_639_1: String?,
    val name: String
)