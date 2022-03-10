package ir.behnawwm.watchlist.core.utils.extension

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ir.behnawwm.watchlist.core.platform.BaseActivity

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun Fragment.close() = fragmentManager?.popBackStack()

//val Fragment.viewContainer: View get() = (activity as BaseActivity).binding.fragmentContainer

val Fragment.appContext: Context get() = activity?.applicationContext!!
