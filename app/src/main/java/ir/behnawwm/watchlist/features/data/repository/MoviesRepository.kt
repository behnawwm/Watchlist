package ir.behnawwm.watchlist.features.data.repository

import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.core.exception.Failure
import ir.behnawwm.watchlist.core.functional.Either
import ir.behnawwm.watchlist.core.utils.NetworkHandler
import ir.behnawwm.watchlist.features.data.remote.api_service.MoviesService
import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.MovieDetails
import ir.behnawwm.watchlist.features.data.remote.dto.popular_movies.TmdbPageResult
import retrofit2.Call
import javax.inject.Inject

interface MoviesRepository {
    //    fun movies(): Either<Failure, List<Movie>>
    fun movieDetails(movieId: Int): Either<Failure, MovieDetails>
    fun popularMovies(): Either<Failure, TmdbPageResult>
    fun topRatedMovies(): Either<Failure, TmdbPageResult>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: MoviesService
    ) : MoviesRepository {

        override fun movieDetails(movieId: Int): Either<Failure, MovieDetails> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.movieDetails(movieId,GeneralConstants.TMDB_TOKEN),
                    { it },
                    MovieDetails.empty
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun popularMovies(): Either<Failure, TmdbPageResult> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.popularMovies(GeneralConstants.TMDB_TOKEN),
                    { it },
                    TmdbPageResult.empty
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun topRatedMovies(): Either<Failure, TmdbPageResult> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    service.topRatedMovies(GeneralConstants.TMDB_TOKEN),
                    { it },
                    TmdbPageResult.empty
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun <T, R> request(
            call: Call<T>,
            transform: (T) -> R,
            default: T
        ): Either<Failure, R> {
            return try {
                val response = call.execute()
                when (response.isSuccessful) {
                    true -> Either.Right(transform((response.body() ?: default)))
                    false -> Either.Left(Failure.ServerError)
                }
            } catch (exception: Throwable) {
                Either.Left(Failure.ServerError)
            }
        }
    }
}
