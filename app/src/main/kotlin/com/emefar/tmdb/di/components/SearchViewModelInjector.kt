package com.emefar.tmdb.di.components

import com.emefar.tmdb.di.module.NetworkModule
import com.emefar.tmdb.ui.search.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface SearchViewModelInjector {

    fun inject(searchViewModel: SearchViewModel)

    @Component.Builder
    interface Builder {
        fun build(): SearchViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}