package ir.behnawwm.watchlist.features.data.remote.dto.popular_movies

import ir.behnawwm.watchlist.features.data.remote.dto.Movie
import ir.behnawwm.watchlist.features.data.remote.dto.MovieDetails

data class PopularMovie(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
) {
    companion object {
        val empty = PopularMovie(0, emptyList(), 0, 0)
    }
}
