package ir.behnawwm.watchlist.core.navigation

import android.content.Context
import ir.behnawwm.watchlist.features.presentation.login.Authenticator
import ir.behnawwm.watchlist.features.presentation.login.LoginActivity
import ir.behnawwm.watchlist.features.presentation.main.MainActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor(private val authenticator: Authenticator) {

    fun showMain(context: Context) {
        when (authenticator.userLoggedIn()) {
            true -> showMovies(context)
            false -> showLogin(context)
        }
    }

    private fun showLogin(context: Context) =
        context.startActivity(LoginActivity.callingIntent(context))

    private fun showMovies(context: Context) =
        context.startActivity(MainActivity.callingIntent(context))

}


