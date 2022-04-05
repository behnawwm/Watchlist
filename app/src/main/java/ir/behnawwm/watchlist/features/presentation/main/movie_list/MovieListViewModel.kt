package ir.behnawwm.watchlist.features.presentation.main.movie_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.core.functional.Event
import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.core.platform.BaseViewModel
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbMovie
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbPageResult
import ir.behnawwm.watchlist.features.domain.use_case.*
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val insertSavedMovie: InsertSavedMovie,
    private val deleteSavedMovie: DeleteSavedMovie,
    private val getAllSavedMovies: GetAllSavedMovies
) :
    BaseViewModel() {

    private val _popularMovies: MutableLiveData<Event<List<MovieView>>> = MutableLiveData()
    val popularMovies: LiveData<Event<List<MovieView>>> = _popularMovies

    private val _topRatedMovies: MutableLiveData<Event<List<MovieView>>> = MutableLiveData()
    val topRatedMovies: LiveData<Event<List<MovieView>>> = _topRatedMovies

    private val _savedMovieStatus: MutableLiveData<Event<Boolean>> =
        MutableLiveData() //todo change Boolean
    val savedMovieStatus: LiveData<Event<Boolean>> = _savedMovieStatus

    private val _savedMovies: MutableLiveData<Event<List<MovieView>>> = MutableLiveData()
    val savedMovies: LiveData<Event<List<MovieView>>> = _savedMovies

    fun loadSavedMoviesList() = //todo IMPORTANT get movies with pagination methods
        getAllSavedMovies(UseCase.None, viewModelScope) {
            it.fold(::handleFailure, ::handleSavedMoviesList)
        }

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


    private fun handleSavedMoviesList(savedMovies: List<MovieEntity>) {
        _savedMovies.value = Event(savedMovies.map { it.toMovieView() })
    }

    private fun handlePopularMovieList(movies: TmdbPageResult<TmdbMovie>) {
        val savedMoviesIdList = savedMovies.value?.peekContent().orEmpty().map { it.id }
        _popularMovies.value = Event(movies.results.map {it.toMovieView()})
    }

    private fun handleTopRatedMovieList(movies: TmdbPageResult<TmdbMovie>) {
        val savedMoviesIdList = savedMovies.value?.peekContent().orEmpty().map { it.id }

        _topRatedMovies.value = Event(movies.results.map { it.toMovieView() })
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