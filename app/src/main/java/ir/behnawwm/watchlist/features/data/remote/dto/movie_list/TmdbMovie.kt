package ir.behnawwm.watchlist.features.data.remote.dto.movie_list

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

@JsonClass(generateAdapter = true)
data class TmdbMovie(
    val adult: Boolean?,

    @Json(name = "backdrop_path")
    val backdropPath: String?,

    @Json(name = "genre_ids")
    val genreIds: List<Int>?,

    val id: Int,

    @Json(name = "original_language")
    val originalLanguage: String?,

    @Json(name = "original_title")
    val originalTitle: String?,

    val overview: String?,

    val popularity: Double?,

    @Json(name = "poster_path")
    val posterPath: String?,

    @Json(name = "release_date")
    val releaseDate: String?,

    val title: String?,

    val video: Boolean?,

    @Json(name = "vote_average")
    val voteAverage: Double?,

    @Json(name = "vote_count")
    val voteCount: Int?
) {
    fun toMovieView() = MovieView(id, posterPath, title.orEmpty(), voteAverage ?: 0.0, false)
}