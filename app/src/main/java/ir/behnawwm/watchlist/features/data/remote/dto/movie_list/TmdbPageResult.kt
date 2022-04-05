package ir.behnawwm.watchlist.features.data.remote.dto.movie_list

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbPageResult<T>(
    val page: Int,
    val results: List<T>,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_results")
    val totalResults: Int
) {
    companion object {
        val empty = TmdbPageResult<TmdbMovie>(0, emptyList(), 0, 0)
    }
}
