package com.emefar.tmdb.base

import androidx.lifecycle.ViewModel
import com.emefar.tmdb.di.components.DaggerFavoritesViewModelInjector
import com.emefar.tmdb.di.components.DaggerLoginViewModelInjector
import com.emefar.tmdb.di.components.DaggerSearchViewModelInjector
import com.emefar.tmdb.di.components.DaggerWatchlistViewModelInjector
import com.emefar.tmdb.di.module.NetworkModule
import com.emefar.tmdb.ui.favorites.FavoritesViewModel
import com.emefar.tmdb.ui.login.LoginViewModel
import com.emefar.tmdb.ui.search.SearchViewModel
import com.emefar.tmdb.ui.watchlist.WatchlistViewModel
import com.emefar.tmdb.utils.androidThread
import com.emefar.tmdb.utils.ioThread
import com.emefar.tmdb.utils.test.EspressoIdlingResource
import io.reactivex.Maybe
import io.reactivex.MaybeObserver

abstract class BaseViewModel : ViewModel() {
    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is LoginViewModel -> DaggerLoginViewModelInjector // LoginViewModelInjector
                .builder()
                .networkModule(NetworkModule)
                .build().inject(this)
            is SearchViewModel -> DaggerSearchViewModelInjector // SearchViewModelInjector
                .builder()
                .networkModule(NetworkModule)
                .build().inject(this)
            is WatchlistViewModel -> DaggerWatchlistViewModelInjector // WatchlistViewModelInjector
                .builder()
                .networkModule(NetworkModule)
                .build().inject(this)
            is FavoritesViewModel -> DaggerFavoritesViewModelInjector // FavoritesViewModelInjector
                .builder()
                .networkModule(NetworkModule)
                .build().inject(this)
        }
    }

    fun <T, M : MaybeObserver<T>> apiCall(api: Maybe<T>, maybeObserver: M) {
        api.subscribeOn(ioThread())
            .doOnSubscribe { EspressoIdlingResource.increment() }
            .doFinally { EspressoIdlingResource.decrement() }
            .observeOn(androidThread())
            .subscribe(maybeObserver)
    }
}