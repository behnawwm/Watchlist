package ir.behnawwm.watchlist.features.data.remote.dto.movie_list

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TmdbPageResult(
    val page: Int,
    val results: List<TmdbMovie>,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_results")
    val totalResults: Int
) {
    companion object {
        val empty = TmdbPageResult(0, emptyList(), 0, 0)
    }
}
