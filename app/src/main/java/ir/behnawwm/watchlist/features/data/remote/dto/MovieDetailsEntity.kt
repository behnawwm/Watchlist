package ir.behnawwm.watchlist.features.data.remote.dto

data class MovieDetailsEntity(
    private val id: Int,
    private val title: String,
    private val poster: String,
    private val summary: String,
    private val cast: String,
    private val director: String,
    private val year: Int,
    private val trailer: String
) {

    companion object {
        val empty = MovieDetailsEntity(
            0, "", "","",
            "", "", 0,""
        )
    }

    fun toMovieDetails() = MovieDetails(id, title, poster, summary, cast, director, year, trailer)
}
