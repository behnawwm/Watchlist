package ir.behnawwm.watchlist.features.presentation.main.movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.core.platform.BaseViewModel
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.MovieDetails
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits.MovieCredits
import ir.behnawwm.watchlist.features.domain.use_case.GetMovieDetails
import ir.behnawwm.watchlist.features.domain.use_case.GetMovieDetailsCredits
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails,
    private val getMovieDetailsCredits: GetMovieDetailsCredits
) : BaseViewModel() {

    private val _movieDetails: MutableLiveData<MovieDetailsView> = MutableLiveData()
    val movieDetails: LiveData<MovieDetailsView> = _movieDetails

    private val _movieCredits: MutableLiveData<MovieCreditsView> = MutableLiveData()
    val movieCredits: LiveData<MovieCreditsView> = _movieCredits

    fun loadAll(movieId: Int) { //todo make these requests parallel and wait for both results
        loadMovieCredits(movieId)
        loadMovieDetails(movieId)
    }

    private fun loadMovieDetails(movieId: Int) =
        getMovieDetails(GetMovieDetails.Params(movieId), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleMovieDetails
            )
        }

    private fun loadMovieCredits(movieId: Int) =
        getMovieDetailsCredits(GetMovieDetailsCredits.Params(movieId), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleMovieCredits
            )
        }

    private fun handleMovieDetails(movie: MovieDetails) {
        _movieDetails.value = MovieDetailsView(
            movie.id,
            movie.title,
            GeneralConstants.TMDB_IMAGE_PREFIX + movie.poster_path,
            movie.overview,
            movie.vote_average,
            movie.overview,
            movie.overview,
            movie.budget,
            movie.overview,
            movie.genres
        )
    }

    private fun handleMovieCredits(credits: MovieCredits) {
        _movieCredits.value = MovieCreditsView(
            credits.id,
            credits.cast.map { it.toCastView() }
        )
    }


}