package ir.behnawwm.watchlist.features.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.core.platform.BaseActivity

class MainActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

//    override fun fragment() = MoviesFragment()
}