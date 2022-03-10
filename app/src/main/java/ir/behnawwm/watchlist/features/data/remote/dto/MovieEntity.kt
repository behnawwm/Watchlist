package ir.behnawwm.watchlist.features.data.remote.dto

data class MovieEntity(private val id: Int, private val poster: String) {
    fun toMovie() = Movie(id, poster)
}
