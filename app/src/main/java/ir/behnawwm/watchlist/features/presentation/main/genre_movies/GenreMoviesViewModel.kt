package ir.behnawwm.watchlist.features.presentation.main.genre_movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.behnawwm.watchlist.core.platform.BaseViewModel
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbMovie
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbPageResult
import ir.behnawwm.watchlist.features.domain.use_case.GetMoviesByGenre
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView
import javax.inject.Inject

@HiltViewModel
class GenreMoviesViewModel @Inject constructor(
    private val getMoviesByGenre: GetMoviesByGenre
) : BaseViewModel() {

    private val _movies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val movies: LiveData<List<MovieView>> = _movies

    fun loadMovies(genreId: Int) {
        getMoviesByGenre(GetMoviesByGenre.Params(genreId), viewModelScope) {
            it.fold(::handleFailure, ::handleMovies)
        }
    }

    private fun handleMovies(result: TmdbPageResult<TmdbMovie>) {
        _movies.value = result.results.map { it.toMovieView() }
    }
}
