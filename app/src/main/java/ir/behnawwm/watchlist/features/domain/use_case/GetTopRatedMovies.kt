package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbMovie
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbPageResult
import ir.behnawwm.watchlist.features.domain.repository.MoviesRepository
import javax.inject.Inject

class GetTopRatedMovies
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<TmdbPageResult<TmdbMovie>, UseCase.None>() {

    override suspend fun run(params: None) = moviesRepository.topRatedMovies()
}
