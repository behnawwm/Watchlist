package ir.behnawwm.watchlist.core.di

import android.app.Application
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.behnawwm.watchlist.BuildConfig
import ir.behnawwm.watchlist.core.constants.GeneralConstants
import ir.behnawwm.watchlist.features.data.database.AppDatabase
import ir.behnawwm.watchlist.features.domain.repository.MoviesRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(GeneralConstants.BASE_URL_TMDB)
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            GeneralConstants.DATABASE_NAME
        ).build()

    @Provides
    fun provideDao(db: AppDatabase) = db.moviesDao

    @Provides
    @Singleton
    fun provideMoviesRepository(dataSource: MoviesRepository.MoviesRepositoryImpl): MoviesRepository =
        dataSource

}
