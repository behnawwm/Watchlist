package ir.behnawwm.watchlist.features.data.remote.dto.search_movie

import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbMovie

data class MovieSearchResponse(
    val page: Int,
    val results: List<TmdbMovie>,
    val total_pages: Int,
    val total_results: Int
) {
    companion object {
        val empty = MovieSearchResponse(0, emptyList(), 0, 0)
    }
}