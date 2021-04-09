package com.emefar.tmdb.di.components

import com.emefar.tmdb.di.module.NetworkModule
import com.emefar.tmdb.ui.favorites.FavoritesViewModel
import com.emefar.tmdb.ui.search.SearchViewModel
import com.emefar.tmdb.ui.watchlist.WatchlistViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface WatchlistViewModelInjector {

    fun inject(watchlistViewModel: WatchlistViewModel)

    @Component.Builder
    interface Builder {
        fun build(): WatchlistViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}