package com.emefar.tmdb.ui.login

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.emefar.tmdb.R
import com.emefar.tmdb.base.App
import com.emefar.tmdb.base.BaseViewModel
import com.emefar.tmdb.model.LoginModel
import com.emefar.tmdb.network.NetworkApi
import com.emefar.tmdb.utils.LDR
import io.reactivex.disposables.Disposable
import java.util.HashMap
import javax.inject.Inject


class LoginViewModel : BaseViewModel() {

    @Inject
    lateinit var networkApi: NetworkApi

    private val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val searchLiveData: MutableLiveData<LDR<LoginModel>> = MutableLiveData()

    val emailField = ObservableField<String>()
    val passField = ObservableField<String>()

    private var subscription: Disposable? = null


    fun login(map: MutableMap<String?, Any?>) = apiCall(
        networkApi.postLogin("Baerer EmefarSpecialAuthCode", "tr", map),
        MyMaybeObserverLogin(loadingVisibility, searchLiveData)
    )

    fun loginWebsiteClick(view: View) {
        searchLiveData.postValue(LDR.error(App.res.getString(R.string.coming_soon)))
    }

    fun loginClick(view: View) {

        if (emailField.get().isNullOrBlank()) {
            searchLiveData.postValue(LDR.error(App.res.getString(R.string.email_empty)))
            return
        } else if (passField.get().isNullOrBlank()) {
            searchLiveData.postValue(LDR.error(App.res.getString(R.string.pass_empty)))
            return
        }


     /*   if (emailField.get().toString() == "mervan@gmail.com" && passField.get().toString() == "123456"){

        }else{

        }*/

        val map: MutableMap<String?, Any?> = HashMap()
        map["email"] = emailField.get().toString()
        map["password"] = passField.get().toString()

        login(map) // It gives always error.


    }


    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }
}
