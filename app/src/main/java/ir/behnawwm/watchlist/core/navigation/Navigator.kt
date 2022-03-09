package ir.behnawwm.watchlist.core.navigation

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.FragmentActivity
import ir.behnawwm.watchlist.features.login.Authenticator
import ir.behnawwm.watchlist.features.login.LoginActivity
import ir.behnawwm.watchlist.features.main.MainActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    private fun showLogin(context: Context) =
        context.startActivity(LoginActivity.callingIntent(context))

    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showMovies(context)
            false -> showLogin(context)
        }
    }

    private fun showMovies(context: Context) =
        context.startActivity(MainActivity.callingIntent(context))

}


