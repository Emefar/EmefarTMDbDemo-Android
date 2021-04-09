package com.emefar.tmdb.ui.search

import androidx.lifecycle.MutableLiveData
import com.emefar.tmdb.base.BaseResponseMovie
import com.emefar.tmdb.base.BaseViewModel
import com.emefar.tmdb.network.NetworkApi
import com.emefar.tmdb.utils.LDR
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SearchViewModel : BaseViewModel() {

    @Inject
    lateinit var networkApi: NetworkApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val searchLiveData: MutableLiveData<LDR<BaseResponseMovie>> = MutableLiveData()

    private var subscription: Disposable? = null

    fun loadMovies(searchQuery: String, page: Int) = apiCall(
        networkApi.searchMovies(searchQuery, page.toString()),
        MyMaybeObserverSearch(loadingVisibility, searchLiveData, page)
    )


    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }
}