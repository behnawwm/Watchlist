package ir.behnawwm.watchlist.features.data.remote.api_service

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService
@Inject constructor(retrofit: Retrofit) : MoviesApi {
    private val moviesApi by lazy { retrofit.create(MoviesApi::class.java) }

//    override fun movies() = moviesApi.movies()
//    override fun movieDetails(movieId: Int) = moviesApi.movieDetails(movieId)

    override fun movieDetails(movieId: Int,token: String)= moviesApi.movieDetails(movieId,token)

    override fun popularMovies(token: String)= moviesApi.popularMovies(token)
}
