package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.MovieDetails
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits.MovieCredits

import ir.behnawwm.watchlist.features.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsCredits
@Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<MovieCredits, GetMovieDetailsCredits.Params>() {

    override suspend fun run(params: Params) = moviesRepository.movieDetailsCredits(params.id)

    data class Params(val id: Int)
}
