package com.emefar.tmdb.base


import android.os.Parcelable
import com.emefar.tmdb.model.MovieItem
import com.google.gson.annotations.SerializedName

data class BaseResponseMovie(
    @SerializedName("results")
    val movies: List<MovieItem>,
    var page: Int = 1
)