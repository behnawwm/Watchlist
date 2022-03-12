package ir.behnawwm.watchlist.features.data.database.dao

import androidx.room.*
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity


@Dao
interface MoviesDao {

    @Query("SELECT * FROM MovieEntity")
    suspend fun getAllSavedMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSavedMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteSavedMovie(movie: MovieEntity)

}