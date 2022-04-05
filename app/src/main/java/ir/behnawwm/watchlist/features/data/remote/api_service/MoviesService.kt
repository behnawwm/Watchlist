package ir.behnawwm.watchlist.features.data.remote.api_service

import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.credits.MovieCredits
import ir.behnawwm.watchlist.features.data.remote.dto.person_details.PersonDetails
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService
@Inject constructor(retrofit: Retrofit) : MoviesApi {
    private val moviesApi by lazy { retrofit.create(MoviesApi::class.java) }

    override fun movieDetails(movieId: Int, token: String) = moviesApi.movieDetails(movieId, token)

    override fun personDetails(personId: Int, token: String) =
        moviesApi.personDetails(personId, token)

    override fun movieDetailsCredits(movieId: Int, token: String) =
        moviesApi.movieDetailsCredits(movieId, token)

    override fun searchMovie(token: String, query: String) = moviesApi.searchMovie(token, query)

    override fun popularMovies(token: String) = moviesApi.popularMovies(token)

    override fun topRatedMovies(token: String) = moviesApi.topRatedMovies(token)
}
