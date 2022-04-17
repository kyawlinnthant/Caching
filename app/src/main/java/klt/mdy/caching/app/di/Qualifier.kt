package klt.mdy.caching.app.di

import javax.inject.Qualifier

object Qualifier {
    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class Io
}