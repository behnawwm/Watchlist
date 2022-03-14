package ir.behnawwm.watchlist.core.utils.extension

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ir.behnawwm.watchlist.R
import ir.behnawwm.watchlist.features.presentation.main.MainActivity

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

fun Fragment.close() = findNavController().popBackStack()

val Fragment.viewContainer: View get() = (activity as MainActivity).binding.fragmentContainer

val Fragment.appContext: Context get() = activity?.applicationContext!!

fun Fragment.notify(message:String) = Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

fun Fragment.notify(@StringRes message: Int) = Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

fun Fragment.showProgress() = progressStatus(View.VISIBLE)

fun Fragment.hideProgress() = progressStatus(View.GONE)

private fun Fragment.progressStatus(viewStatus: Int) =      //todo change
    with(activity) { if (this is MainActivity) this.binding.progressBar.visibility = viewStatus }

fun Fragment.notifyWithAction(
    @StringRes message: Int,
    @StringRes actionText: Int,
    action: () -> Any
) {
    val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
    snackBar.setAction(actionText) { _ -> action.invoke() }
    snackBar.setActionTextColor(ContextCompat.getColor(appContext, R.color.colorTextPrimary))
    snackBar.duration = Snackbar.LENGTH_SHORT
    snackBar.show()
}
