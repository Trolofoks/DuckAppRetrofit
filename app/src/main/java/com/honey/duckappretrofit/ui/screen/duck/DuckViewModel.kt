package com.honey.duckappretrofit.ui.screen.duck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honey.duckappretrofit.data.model.Duck
import com.honey.duckappretrofit.data.remote.RandomDuckApiService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class DuckViewModel(private val duckApi: RandomDuckApiService) : ViewModel() {
    private val _duck = MutableSharedFlow<Duck>(replay = 1)
    val duck : SharedFlow<Duck> = _duck.asSharedFlow()


    fun getDuck(){
        viewModelScope.launch {
            val randomDuck = duckApi.getDuck()
            _duck.tryEmit(randomDuck)
        }
    }
}