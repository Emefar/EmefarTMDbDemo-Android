package com.emefar.tmdb.ui.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.emefar.tmdb.R
import com.emefar.tmdb.base.App
import com.emefar.tmdb.base.BaseResponseMovie
import com.emefar.tmdb.model.LoginModel
import com.emefar.tmdb.utils.LDR
import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable


class MyMaybeObserverLogin(
    private val loadingVisibility: MutableLiveData<Int>? = null,
    private val apiSearchResult: MutableLiveData<LDR<LoginModel>> = MutableLiveData()
) : MaybeObserver<LoginModel> {

    override fun onComplete() {

    }

    override fun onSuccess(t: LoginModel) {
        loadingVisibility?.value = View.GONE

        if (t.userId.isNotEmpty()) {
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
        apiSearchResult.postValue(LDR.started())
    }

}