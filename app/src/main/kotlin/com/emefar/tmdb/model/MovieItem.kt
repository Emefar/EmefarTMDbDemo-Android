package com.emefar.tmdb.model


import android.os.Parcelable
import com.emefar.tmdb.base.Configs
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieItem(
    val id: Int,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String
): Parcelable {

    fun getThumbURL(): String {
        return Configs.IMAGE_URL + "w500" + posterPath
    }

    fun getVoteAverage(): String {
        return voteAverage.toString()
    }

    fun getSearchName():String{
        if (releaseDate.isNullOrEmpty()){
            return title
        }
        return  "$title ($releaseDate)"
    }

}
