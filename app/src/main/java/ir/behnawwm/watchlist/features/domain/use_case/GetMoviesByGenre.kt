package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbMovie
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbPageResult
import ir.behnawwm.watchlist.features.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesByGenre @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCase<TmdbPageResult<TmdbMovie>, GetMoviesByGenre.Params>() {

    override suspend fun run(params: Params) =
        moviesRepository.moviesByGenre(params.genreId)

    data class Params(val genreId: Int)
}
