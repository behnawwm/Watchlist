package ir.behnawwm.watchlist.features.data.remote.dto.movie_list


data class TmdbPageResult(
    val page: Int,
    val results: List<TmdbMovie>,
    val total_pages: Int,
    val total_results: Int
) {
    companion object {
        val empty = TmdbPageResult(0, emptyList(), 0, 0)
    }
}
