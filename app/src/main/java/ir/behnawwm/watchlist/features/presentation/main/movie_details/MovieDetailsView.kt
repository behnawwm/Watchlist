package ir.behnawwm.watchlist.features.presentation.main.movie_details

import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.Genre

data class MovieDetailsView(
    val id: Int,
    val title: String,
    val poster: String,
    val summary: String,
    val rating: Double,
    val cast: String,
    val director: String,
    val year: Int,
    val trailer: String,
    val genres: List<Genre>
)
