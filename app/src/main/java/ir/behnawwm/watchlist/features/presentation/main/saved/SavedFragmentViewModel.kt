package ir.behnawwm.watchlist.features.presentation.main.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.core.platform.BaseViewModel
import ir.behnawwm.watchlist.databinding.FragmentSavedBinding
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity
import ir.behnawwm.watchlist.features.data.remote.dto.popular_movies.TmdbPageResult
import ir.behnawwm.watchlist.features.domain.use_case.GetAllSavedMovies
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView
import javax.inject.Inject

@HiltViewModel
class SavedFragmentViewModel @Inject constructor(
    private val getAllSavedMovies: GetAllSavedMovies,
) : BaseViewModel() {

    private val _savedMovies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val savedMovies: LiveData<List<MovieView>> = _savedMovies

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


}