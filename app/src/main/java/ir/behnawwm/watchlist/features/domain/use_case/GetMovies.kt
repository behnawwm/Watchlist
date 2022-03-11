package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.functional.Either
import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.remote.dto.Movie
import ir.behnawwm.watchlist.features.data.repository.MoviesRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetMovies
@Inject constructor(private val moviesRepository: MoviesRepository) : UseCase<List<Movie>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Movie>> {
        delay(3000)
       return moviesRepository.movies()
    }
}
