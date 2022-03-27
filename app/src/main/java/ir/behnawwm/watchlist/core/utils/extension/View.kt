package ir.behnawwm.watchlist.core.utils.extension

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import coil.ImageLoader
import coil.load
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.constants.GeneralConstants.TMDB_IMAGE_PREFIX_ORIGINAL
import ir.behnawwm.watchlist.core.constants.GeneralConstants.TMDB_IMAGE_PREFIX_W200


fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadImage(
    uri: Any
) {
    this.load(uri, CoilUtils.imageLoader(context.applicationContext))
}

fun ImageView.loadTmdbImageLowQuality(
    uri: String?
) {
    if (uri.isNullOrEmpty())
        this.loadImage(R.drawable.no_photo_profile_placeholder)
    else
        this.loadImage(TMDB_IMAGE_PREFIX_W200 + uri)
}

fun ImageView.loadTmdbImageOriginalQuality(
    uri: String?
) {
    if (uri.isNullOrEmpty())
        this.loadImage(R.drawable.no_photo_profile_placeholder)
    else
        this.loadImage(TMDB_IMAGE_PREFIX_ORIGINAL + uri)
}

