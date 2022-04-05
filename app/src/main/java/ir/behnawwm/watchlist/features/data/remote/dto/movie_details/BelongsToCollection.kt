package ir.behnawwm.watchlist.features.data.remote.dto.movie_details

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BelongsToCollection(
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val name: String?,
    @Json(name = "poster_path")
    val posterPath: String?
)