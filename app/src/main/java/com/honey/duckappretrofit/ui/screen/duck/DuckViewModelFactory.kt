package com.honey.duckappretrofit.ui.screen.duck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.honey.duckappretrofit.data.remote.RandomDuckApiService

class DuckViewModelFactory(private val duckApiService: RandomDuckApiService) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DuckViewModel(duckApi = duckApiService) as T
    }
}