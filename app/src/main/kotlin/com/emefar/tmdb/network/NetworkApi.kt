package com.emefar.tmdb.network

import com.emefar.tmdb.base.BaseResponseMovie
import com.emefar.tmdb.model.LoginModel
import io.reactivex.Maybe
import retrofit2.http.*

interface NetworkApi {

    @GET("3/discover/movie?api_key=57a4269b6c0098d52f01d65572e57972&sort_by=popularity.desc")
    fun getPopularMovies(): Maybe<BaseResponseMovie>

    @GET("3/search/movie?api_key=57a4269b6c0098d52f01d65572e57972")
    fun searchMovies(
        @Query("query") searchPhrase: String,
        @Query("page") page: String
    ): Maybe<BaseResponseMovie>


    @POST("/mfr/login")
    fun postLogin(
        @Header("Authorization") auth: String?, @Header("lang") lang: String?,
        @Body body: MutableMap<String?, Any?>
    ): Maybe<LoginModel>


}