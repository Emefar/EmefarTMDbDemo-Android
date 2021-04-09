package com.emefar.tmdb.di.module

import com.emefar.tmdb.base.Configs.BASE_URL
import com.emefar.tmdb.utils.ApiUtils
import com.emefar.tmdb.utils.MOCK_PATH
import dagger.Module
import dagger.Provides
import io.reactivex.Maybe
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused", "UNUSED_PARAMETER")
object NetworkModule {


    @Provides
    @Singleton
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).build()
    }
}