package klt.mdy.caching.app.di

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import klt.mdy.caching.MovieDatabase
import klt.mdy.caching.app.common.Constant
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    @Singleton
    fun providesMovieSqlDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(
            schema = MovieDatabase.Schema,
            context = context,
            name = Constant.MOVIE_DB
        )
    }

    @Provides
    @Singleton
    fun providesMovieDatabase(driver: SqlDriver): MovieDatabase {
        return MovieDatabase(driver = driver)
    }


}
