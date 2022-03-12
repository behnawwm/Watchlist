package ir.behnawwm.watchlist.features.presentation.main.movie_list

import android.os.Parcelable
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieView(
    val id: Int,
    val poster: String,
    val title: String,
    val rating: Double
) : Parcelable {


    fun toMovieListItem(listener: (MovieView, Boolean) -> Unit) = MovieListItem(this, listener)
    fun toMovieEntity() = MovieEntity(id,poster,title,rating)
}
