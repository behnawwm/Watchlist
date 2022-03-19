package ir.behnawwm.watchlist.features.presentation.main.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.behnawwm.watchlist.core.functional.Event
import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.core.platform.BaseViewModel
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity
import ir.behnawwm.watchlist.features.domain.use_case.DeleteSavedMovie
import ir.behnawwm.watchlist.features.domain.use_case.GetAllSavedMovies
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView
import javax.inject.Inject

@HiltViewModel
class SavedFragmentViewModel @Inject constructor(
    private val getAllSavedMovies: GetAllSavedMovies,
    private val deleteSavedMovie: DeleteSavedMovie
) : BaseViewModel() {

    private val _savedMovies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val savedMovies: LiveData<List<MovieView>> = _savedMovies

    private val _savedMovieStatus: MutableLiveData<Event<Boolean>> =
        MutableLiveData() //todo change Boolean
    val savedMovieStatus: LiveData<Event<Boolean>> = _savedMovieStatus

    fun loadSavedMovies() {
        getAllSavedMovies(UseCase.None, viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleSavedMovieList
            )
        }
    }

    private fun handleSavedMovieList(movies: List<MovieEntity>) {
        _savedMovies.value = movies.map { it.toMovieView() }
    }

    fun deleteSavedMovie(movie: MovieView) {
        deleteSavedMovie(DeleteSavedMovie.Params(movie.toMovieEntity()), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleSavedMovieDeletion
            )
        }
    }

    private fun handleSavedMovieDeletion(none: UseCase.None) {
        _savedMovieStatus.value = Event(false)  //todo better way
    }
}