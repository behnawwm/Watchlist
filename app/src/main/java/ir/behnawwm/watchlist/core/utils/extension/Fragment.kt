package ir.behnawwm.watchlist.core.utils.extension

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ir.behnawwm.watchlist.core.platform.BaseActivity
import ir.behnawwm.watchlist.core.platform.BaseFragment

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun BaseFragment.close() = fragmentManager?.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).binding.fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!
