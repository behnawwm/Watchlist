package ir.behnawwm.watchlist.core.utils.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.annotation.LayoutRes
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

fun View.gone() {
    this.visibility = View.GONE
}
fun View.invisible() {
    this.visibility = View.INVISIBLE
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

fun View.showWithFade() {
    this.visible()
    val fadeIn = AlphaAnimation(0f, 1f).apply {
        interpolator = DecelerateInterpolator()
        duration = 500
//        startOffset = 1000
        fillAfter = true
    }
    this.startAnimation(fadeIn)
}
