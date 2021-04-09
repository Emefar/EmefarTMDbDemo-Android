package com.emefar.tmdb.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WatchlistViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(WatchlistViewModel::class.java)) {
            return WatchlistViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}