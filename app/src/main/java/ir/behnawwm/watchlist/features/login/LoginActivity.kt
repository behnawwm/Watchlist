package ir.behnawwm.watchlist.features.login

import android.content.Context
import android.content.Intent
import ir.behnawwm.watchlist.core.platform.BaseActivity

class LoginActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override fun fragment() = LoginFragment()
}
