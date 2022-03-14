package ir.behnawwm.watchlist.features.presentation.main.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.core.functional.Event
import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.core.platform.BaseViewModel
import ir.behnawwm.watchlist.features.data.remote.dto.popular_movies.TmdbPageResult
import ir.behnawwm.watchlist.features.domain.use_case.GetPopularMovies
import ir.behnawwm.watchlist.features.domain.use_case.GetTopRatedMovies
import ir.behnawwm.watchlist.features.domain.use_case.InsertSavedMovie
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val insertSavedMovie: InsertSavedMovie,
) :
    BaseViewModel() {

    private val _popularMovies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val popularMovies: LiveData<List<MovieView>> = _popularMovies

    private val _topRatedMovies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val topRatedMovies: LiveData<List<MovieView>> = _topRatedMovies

    private val _savedMovieStatus: MutableLiveData<Event<Boolean>> = MutableLiveData() //todo change Boolean
    val savedMovieStatus: LiveData<Event<Boolean>> = _savedMovieStatus


    fun loadPopularMovies() =
        getPopularMovies(UseCase.None, viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handlePopularMovieList
            )
        }

    fun loadTopRatedMovies() =
        getTopRatedMovies(UseCase.None, viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleTopRatedMovieList
            )
        }

    private fun handlePopularMovieList(movies: TmdbPageResult) {
        _popularMovies.value = movies.results.map {
            MovieView(
                it.id,
                GeneralConstants.TMDB_IMAGE_PREFIX + it.poster_path,
                it.title,
                it.vote_average
            )
        }
    }

    private fun handleTopRatedMovieList(movies: TmdbPageResult) {
        _topRatedMovies.value = movies.results.map {
            MovieView(
                it.id,
                GeneralConstants.TMDB_IMAGE_PREFIX + it.poster_path,
                it.title,
                it.vote_average
            )
        }
    }

    fun insertSavedMovie(movie: MovieView) {
        insertSavedMovie(InsertSavedMovie.Params(movie.toMovieEntity()), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleSavedMovieInsertion
            )
        }
    }

    private fun handleSavedMovieInsertion(none: UseCase.None) {
        _savedMovieStatus.value = Event(true)  //todo better way
    }

    fun removeSavedMovie(movie: MovieView) {
        _savedMovieStatus.value = Event(true)  //todo better way
    }
}