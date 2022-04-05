package ir.behnawwm.watchlist.features.presentation.main.person_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.behnawwm.watchlist.core.platform.BaseViewModel
import ir.behnawwm.watchlist.features.data.remote.dto.person_details.PersonDetails
import ir.behnawwm.watchlist.features.domain.use_case.*
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val getPersonDetails: GetPersonDetails
) : BaseViewModel() {

    private val _personDetails: MutableLiveData<PersonDetailsView> = MutableLiveData()
    val personDetails: LiveData<PersonDetailsView> = _personDetails

    fun loadMovieDetails(movieId: Int) =
        getPersonDetails(GetPersonDetails.Params(movieId), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handlePersonDetails
            )
        }

    private fun handlePersonDetails(person: PersonDetails) {
        _personDetails.value = person.toPersonDetailsView()
    }


}