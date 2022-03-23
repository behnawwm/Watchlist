package ir.behnawwm.watchlist.features.presentation.main.movie_details

import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits.Cast
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits.Crew

data class MovieCreditsView(
    val id: Int,
    val cast: List<CastView>,
//    val crw: List<CrewView>
)
