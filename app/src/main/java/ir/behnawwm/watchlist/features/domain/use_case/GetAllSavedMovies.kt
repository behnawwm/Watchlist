package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity

import ir.behnawwm.watchlist.features.domain.repository.MoviesRepository
import javax.inject.Inject

class GetAllSavedMovies
@Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<List<MovieEntity>, UseCase.None>() {

    override suspend fun run(params: None) = moviesRepository.savedMovies()

}
