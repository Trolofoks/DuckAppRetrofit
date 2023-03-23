package com.honey.duckappretrofit.ui.screen.duck

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.honey.duckappretrofit.data.model.Duck
import com.honey.duckappretrofit.data.model.DuckState
import com.honey.duckappretrofit.data.remote.RandomDuckApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DuckViewModel(private val duckApi: RandomDuckApiService) : ViewModel() {
    private val _duckState = MutableStateFlow(DuckState(loading = true))
    val duckState : StateFlow<DuckState> = _duckState.asStateFlow()

    private val _error = MutableSharedFlow<String>()
    val error : SharedFlow<String> = _error.asSharedFlow()

    fun getDuck(){
        viewModelScope.launch {
            duckApi.getDuck().enqueue(object : Callback<Duck>{
                override fun onResponse(call: Call<Duck>, response: Response<Duck>) {
                    if (response.isSuccessful){
                        val duck = response.body()
                        duck?.let {
                            _duckState.value = DuckState(duck = it, loading = false)
                        }
                    } else {
                        setError(response.message())
                    }
                }
                override fun onFailure(call: Call<Duck>, t: Throwable) {
                    setError("Can't connect to server")
                }
            })
        }
    }
    private fun setError(message: String){
        viewModelScope.launch {
            _error.tryEmit(message)
            delay(10000)
            _error.tryEmit("")
        }
    }
}