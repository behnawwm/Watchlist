package ir.behnawwm.watchlist.features.presentation.main.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.core.platform.BaseViewModel
import ir.behnawwm.watchlist.features.data.remote.dto.popular_movies.TmdbPageResult
import ir.behnawwm.watchlist.features.domain.use_case.GetPopularMovies
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val getMovies: GetPopularMovies) :
    BaseViewModel() {

    private val _movies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val movies: LiveData<List<MovieView>> = _movies

    fun loadMovies() =
        getMovies(UseCase.None(), viewModelScope) { it.fold(::handleFailure, ::handleMovieList) }

    private fun handleMovieList(movies: TmdbPageResult) {
        _movies.value = movies.results.map {
            MovieView(
                it.id,
                GeneralConstants.TMDB_IMAGE_PREFIX + it.poster_path,
                it.title,
                it.vote_average
            )
        }
    }
}