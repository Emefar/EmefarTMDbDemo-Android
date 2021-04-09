package com.emefar.tmdb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SearchViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}