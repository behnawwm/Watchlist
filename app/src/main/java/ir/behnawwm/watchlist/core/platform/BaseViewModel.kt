package ir.behnawwm.watchlist.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.functional.Event

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {

    private val _failure: MutableLiveData<Event<Failure>> = MutableLiveData()
    val failure: LiveData<Event<Failure>> = _failure

    protected fun handleFailure(failure: Failure) {
        _failure.value = Event(failure)
    }

}