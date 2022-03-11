package ir.behnawwm.watchlist.features.data.remote.api_service

import ir.behnawwm.watchlist.features.data.remote.dto.MovieDetailsEntity
import ir.behnawwm.watchlist.features.data.remote.dto.MovieEntity
import ir.behnawwm.watchlist.features.data.remote.dto.popular_movies.PopularMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MoviesApi {
    companion object {
        private const val PARAM_MOVIE_ID = "movieId"
        private const val MOVIES = "movies.json"
        private const val MOVIE_DETAILS = "movie_0{$PARAM_MOVIE_ID}.json"
    }

    @GET(MOVIES)
    fun movies(): Call<List<MovieEntity>>

    @GET(MOVIE_DETAILS)
    fun movieDetails(@Path(PARAM_MOVIE_ID) movieId: Int): Call<MovieDetailsEntity>


    @GET("3/movie/popular/")
    fun popularMovies(@Query("api_key") token: String): Call<PopularMovie>
}