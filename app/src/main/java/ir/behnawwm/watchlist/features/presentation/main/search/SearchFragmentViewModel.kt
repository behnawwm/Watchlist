package ir.behnawwm.watchlist.features.presentation.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.core.platform.BaseViewModel
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbMovie
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbPageResult
import ir.behnawwm.watchlist.features.domain.use_case.SearchMovie
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView
import javax.inject.Inject

@HiltViewModel
class SearchFragmentViewModel @Inject constructor(
    private val searchMovie: SearchMovie
) : BaseViewModel() {

    private val _searchedMovies: MutableLiveData<List<MovieView>> = MutableLiveData()
    val searchedMovies: LiveData<List<MovieView>> = _searchedMovies

    fun loadSearchedMovies(query: String) {
        searchMovie(SearchMovie.Params(query), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleSearchedMovieList
            )
        }
    }

    private fun handleSearchedMovieList(movies: TmdbPageResult<TmdbMovie>) {
        _searchedMovies.value = movies.results.map { it.toMovieView() }
    }


}