package ir.behnawwm.watchlist.features.presentation.main.movie_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieView(
    val id: Int,
    val poster: String,
) : Parcelable {

}
