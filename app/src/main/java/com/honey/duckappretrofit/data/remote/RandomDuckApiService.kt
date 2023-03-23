package com.honey.duckappretrofit.data.remote

import com.honey.duckappretrofit.data.model.Duck
import retrofit2.Call
import retrofit2.http.GET

interface RandomDuckApiService {
    @GET("random")
    suspend fun getDuck(): Call<Duck>
}