package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity

import ir.behnawwm.watchlist.features.domain.repository.MoviesRepository
import javax.inject.Inject

class InsertSavedMovie
@Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<UseCase.None, InsertSavedMovie.Params>() {

    override suspend fun run(params: Params) = moviesRepository.insertSavedMovie(params.movie)

    data class Params(val movie: MovieEntity)
}
