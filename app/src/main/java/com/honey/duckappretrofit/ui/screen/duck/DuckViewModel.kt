package com.honey.duckappretrofit.ui.screen.duck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honey.duckappretrofit.data.model.Duck
import com.honey.duckappretrofit.data.model.DuckState
import com.honey.duckappretrofit.data.remote.RandomDuckApiService
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.await
import java.io.IOException


class DuckViewModel(private val duckApi: RandomDuckApiService) : ViewModel() {
    private val _duck = MutableStateFlow<DuckState>(value = DuckState())
    val duck : StateFlow<DuckState> = _duck.asStateFlow()


    fun getDuck(){
        viewModelScope.launch {
            _duck.tryEmit(DuckState(duck = null, loading = true, error = null))
            try {
                duckApi.getRandomDuck().let { _duck.tryEmit(DuckState(duck = it, loading = false, error = null)) }
            } catch (e: IOException) {
                _duck.tryEmit(DuckState(duck = null, loading = false, error = e.toString()))
            }
        }
    }

}