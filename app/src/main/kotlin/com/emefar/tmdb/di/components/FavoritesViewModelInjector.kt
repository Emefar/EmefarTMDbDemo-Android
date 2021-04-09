package com.emefar.tmdb.di.components

import com.emefar.tmdb.di.module.NetworkModule
import com.emefar.tmdb.ui.favorites.FavoritesViewModel
import com.emefar.tmdb.ui.search.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface FavoritesViewModelInjector {

    fun inject(favoritesViewModel: FavoritesViewModel)

    @Component.Builder
    interface Builder {
        fun build(): FavoritesViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}