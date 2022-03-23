package ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits

import ir.behnawwm.watchlist.features.presentation.main.movie_details.CastView

data class MovieCredits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
) {
    companion object {
        val empty = MovieCredits(emptyList(), emptyList(), 0)
    }

}