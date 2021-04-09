package com.emefar.tmdb.ui.favorites

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.emefar.tmdb.R
import com.emefar.tmdb.base.BaseResponseMovie
import com.emefar.tmdb.base.BaseViewModel
import com.emefar.tmdb.model.MovieItem
import com.emefar.tmdb.network.NetworkApi
import com.emefar.tmdb.utils.LDR
import com.emefar.tmdb.utils.extensions.toast
import com.fxn.stash.Stash
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FavoritesViewModel : BaseViewModel() {

    @Inject
    lateinit var networkApi: NetworkApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val searchLiveData: MutableLiveData<LDR<BaseResponseMovie>> = MutableLiveData()

    private var subscription: Disposable? = null

    fun loadMovies(searchQuery: String, page: Int) = apiCall(
        networkApi.searchMovies(searchQuery, page.toString()),
        MyMaybeObserverFavorites(loadingVisibility, searchLiveData, page)
    )


    fun getFavorites() {

        val favoritesArrayList: ArrayList<MovieItem> =
            Stash.getArrayList<MovieItem>(
                "FAVORITES",
                MovieItem::class.java
            )

        searchLiveData.postValue(LDR.success(BaseResponseMovie(favoritesArrayList)))


    }

    fun removeFromFavorites(id: Int) {

        val favoritesArrayList: ArrayList<MovieItem> =
            Stash.getArrayList<MovieItem>(
                "FAVORITES",
                MovieItem::class.java
            )

        favoritesArrayList.forEachIndexed { index, element ->
            if (index == id) {
                favoritesArrayList.removeAt(index)
                Stash.put("FAVORITES", favoritesArrayList)
                return
            }

        }

    }


    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }
}