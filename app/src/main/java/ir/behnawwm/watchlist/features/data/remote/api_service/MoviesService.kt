package ir.behnawwm.watchlist.features.data.remote.api_service

import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.features.data.remote.dto.popular_movies.PopularMovie
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService
@Inject constructor(retrofit: Retrofit) : MoviesApi {
    private val moviesApi by lazy { retrofit.create(MoviesApi::class.java) }

    override fun movies() = moviesApi.movies()
    override fun movieDetails(movieId: Int) = moviesApi.movieDetails(movieId)
    override fun popularMovies(token: String)= moviesApi.popularMovies(token)
}
