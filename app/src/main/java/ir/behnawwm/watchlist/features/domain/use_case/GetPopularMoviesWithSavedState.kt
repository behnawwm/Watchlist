package ir.behnawwm.watchlist.features.domain.use_case

import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.functional.Either
import ir.behnawwm.watchlist.core.functional.getOrElse
import ir.behnawwm.watchlist.core.interactor.UseCase
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbMovie
import ir.behnawwm.watchlist.features.data.remote.dto.movie_list.TmdbPageResult
import ir.behnawwm.watchlist.features.domain.repository.MoviesRepository
import javax.inject.Inject

//todo check if this is needed
//class GetPopularMoviesWithSavedState
//@Inject constructor(
//    private val getPopularMovies: GetPopularMovies,
//    private val getAllSavedMovies: GetAllSavedMovies
//) : UseCase<List<TmdbMovie>, UseCase.None>() {
//
//    private lateinit var popularMovies: TmdbPageResult<TmdbMovie>
//    private lateinit var savedMovies: List<MovieEntity>
//
//    override suspend fun run(params: None): Either<Failure, List<TmdbMovie>> {
//        getAllSavedMovies(None) {
//            it.fold(::handleFailure, ::handleSavedMovies)
//        }
//        getPopularMovies(None) {
//            it.fold(::handleFailure, ::handlePopularMovies)
//        }
//
//        val popularMoviesWithSavedState =
//            popularMovies.results
//                .map { it.toMovieView() }
//
//        popularMoviesWithSavedState.forEach { movie ->
//            if (savedMovies.any { it.id == movie.id }) {
//                movie.isSaved = true
//            }
//        }
//
//        return Either.Right(popularMoviesWithSavedState)
//    }
//
//    private fun handleSavedMovies(savedMovies: List<MovieEntity>) {
//        this.savedMovies = savedMovies
//    }
//
//    private fun handlePopularMovies(popularMovies: TmdbPageResult<TmdbMovie>) {
//        this.popularMovies = popularMovies
//    }
//
//
//    fun handleFailure(failure: Failure): Either.Left<Failure> {
//        return Either.Left(failure)
//    }
//}
