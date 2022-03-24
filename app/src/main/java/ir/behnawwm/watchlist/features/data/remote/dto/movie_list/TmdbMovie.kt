package ir.behnawwm.watchlist.features.data.remote.dto.movie_list

import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

data class TmdbMovie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
){
    fun toMovieView() = MovieView(id, GeneralConstants.TMDB_IMAGE_PREFIX + poster_path,title,vote_average,false)
}