package com.honey.duckappretrofit.data.remote

import com.honey.duckappretrofit.data.model.Duck
import retrofit2.Call
import retrofit2.http.GET

interface RandomDuckApiService {
    @GET(Endpoints.GET_RANDOM_DUCK)
    suspend fun getRandomDuck(): Duck
}