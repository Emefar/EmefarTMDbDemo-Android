package com.emefar.tmdb.ui.favorites

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.emefar.tmdb.R
import com.emefar.tmdb.base.App
import com.emefar.tmdb.base.BaseResponseMovie
import com.emefar.tmdb.utils.LDR
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable


class MyMaybeObserverFavorites(
    private val loadingVisibility: MutableLiveData<Int>? = null,
    private val apiSearchResult: MutableLiveData<LDR<BaseResponseMovie>> = MutableLiveData(),
    private val page: Int = 0
) : MaybeObserver<BaseResponseMovie> {

    override fun onComplete() {

    }

    override fun onSuccess(t: BaseResponseMovie) {
        loadingVisibility?.value = View.GONE

        if (t.movies.isNotEmpty()) {
            apiSearchResult.postValue(LDR.success(t))
        } else {
            apiSearchResult.postValue(LDR.error(App.res.getString(R.string.something_went_wrong)))
        }
    }

    override fun onError(e: Throwable) {
        loadingVisibility?.value = View.GONE
        apiSearchResult.postValue(LDR.error(App.res.getString(R.string.something_went_wrong) + " -- " + e.localizedMessage))
    }

    override fun onSubscribe(d: Disposable) {
        if (page == 0) {
            loadingVisibility?.value = View.VISIBLE
        }
        apiSearchResult.postValue(LDR.started())
    }

}