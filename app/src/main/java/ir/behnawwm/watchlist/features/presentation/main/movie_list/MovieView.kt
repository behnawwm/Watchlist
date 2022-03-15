package ir.behnawwm.watchlist.features.presentation.main.movie_list

import android.os.Parcelable
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity
import ir.behnawwm.watchlist.features.presentation.main.saved.SavedMovieListItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieView(
    val id: Int,
    val poster: String,
    val title: String,
    val rating: Double,
    var isSaved : Boolean
) : Parcelable {


    fun toMovieListItem(listener: (MovieView, Boolean) -> Unit) = MovieListItem(this, listener)
    fun toSavedMovieListItem() = SavedMovieListItem(this)
    fun toMovieEntity() = MovieEntity(id,title,poster,rating)
}
