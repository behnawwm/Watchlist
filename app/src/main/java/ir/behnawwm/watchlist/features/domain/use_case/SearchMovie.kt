package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.MovieDetails
import ir.behnawwm.watchlist.features.data.remote.dto.search_movie.MovieSearchResponse

import ir.behnawwm.watchlist.features.domain.repository.MoviesRepository
import javax.inject.Inject

class SearchMovie
@Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<MovieSearchResponse, SearchMovie.Params>() {

    override suspend fun run(params: Params) = moviesRepository.searchMovie(params.query)

    data class Params(val query: String)
}
