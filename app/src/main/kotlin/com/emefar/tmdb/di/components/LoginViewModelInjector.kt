package com.emefar.tmdb.di.components

import com.emefar.tmdb.di.module.NetworkModule
import com.emefar.tmdb.ui.login.LoginViewModel
import com.emefar.tmdb.ui.search.SearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface LoginViewModelInjector {

    fun inject(loginViewModel: LoginViewModel)

    @Component.Builder
    interface Builder {
        fun build(): LoginViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}