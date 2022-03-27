package ir.behnawwm.watchlist.features.data.remote.dto.search_movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbMovie

@JsonClass(generateAdapter = true)
data class MovieSearchResponse(     //todo same as TmdbPageResult
    val page: Int,
    val results: List<TmdbMovie>,

    @Json(name = "total_pages")
    val totalPages: Int,

    @Json(name = "total_results")
    val totalResults: Int
) {
    companion object {
        val empty = MovieSearchResponse(0, emptyList(), 0, 0)
    }
}