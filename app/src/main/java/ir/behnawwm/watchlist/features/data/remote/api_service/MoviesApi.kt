package ir.behnawwm.watchlist.features.data.remote.api_service

import ir.behnawwm.watchlist.features.data.remote.dto.movie_details.MovieDetails
import ir.behnawwm.watchlist.features.data.remote.dto.popular_movies.TmdbPageResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MoviesApi {

    @GET("3/movie/popular/")
    fun popularMovies(@Query("api_key") token: String): Call<TmdbPageResult>

    @GET("3/movie/top_rated/")
    fun topRatedMovies(@Query("api_key") token: String): Call<TmdbPageResult>

    @GET("3/movie/{id}")
    fun movieDetails(@Path("id") movieId: Int, @Query("api_key") token: String): Call<MovieDetails>

}