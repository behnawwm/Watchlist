package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.remote.dto.popular_movies.TmdbPageResult
import ir.behnawwm.watchlist.features.data.repository.MoviesRepository
import javax.inject.Inject

class GetTopRatedMovies
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<TmdbPageResult, UseCase.None>() {

    override suspend fun run(params: None) = moviesRepository.topRatedMovies()
}
