package ir.behnawwm.watchlist.features.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.behnawwm.watchlist.features.data.database.dao.MoviesDao
import ir.behnawwm.watchlist.features.data.database.entity.MovieEntity
import ir.behnawwm.watchlist.features.presentation.main.movie_list.MovieView

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val moviesDao: MoviesDao

}