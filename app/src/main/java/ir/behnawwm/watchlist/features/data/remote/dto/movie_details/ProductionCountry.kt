package ir.behnawwm.watchlist.features.data.remote.dto.movie_details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCountry(
    @Json(name = "iso_3166_1")
    val iso_3166_1: String,
    val name: String
)