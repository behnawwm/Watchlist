package ir.behnawwm.watchlist.features.data.remote.dto.movie_details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductionCompany(
    val id: Int,
    @Json(name = "logo_path")
    val logoPath: String?,
    val name: String?,
    @Json(name = "origin_country")
    val originCountry: String?
)