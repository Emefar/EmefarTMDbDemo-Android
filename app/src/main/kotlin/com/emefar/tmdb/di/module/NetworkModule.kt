package com.emefar.tmdb.di.module

import com.emefar.tmdb.base.Configs.BASE_URL
import com.emefar.tmdb.base.Configs.LOG_TAG
import com.emefar.tmdb.network.NetworkApi
import com.emefar.tmdb.utils.ioThread
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@Suppress("unused")
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideNetworkApi(retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java)
    }


    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Singleton
    internal fun provideRetrofitInterface(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(ioThread()))
            .build()
    }

    @Provides
    @Singleton
    fun createHttpClient(): OkHttpClient.Builder {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        val logging = HttpLoggingInterceptor { message ->
            Timber.tag("OkHttp $LOG_TAG").d(message)
        }
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpClientBuilder.addInterceptor(logging)
        okHttpClientBuilder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
            chain.proceed(request.build())
        }
        return okHttpClientBuilder
    }
}