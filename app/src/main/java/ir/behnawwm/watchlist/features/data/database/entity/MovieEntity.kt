package ir.behnawwm.watchlist.features.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val rating: Double
) {

    fun toMovieView() =
        MovieView(id, GeneralConstants.TMDB_IMAGE_PREFIX + posterPath, title, rating,true)
}