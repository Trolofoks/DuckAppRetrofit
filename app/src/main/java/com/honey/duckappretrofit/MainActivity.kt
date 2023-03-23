package com.honey.duckappretrofit

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.honey.duckappretrofit.data.model.Duck
import com.honey.duckappretrofit.data.remote.Endpoints
import com.honey.duckappretrofit.data.remote.RandomDuckApiService
import com.honey.duckappretrofit.ui.screen.duck.DuckScreen
import com.honey.duckappretrofit.ui.screen.duck.DuckViewModel
import com.honey.duckappretrofit.ui.screen.duck.DuckViewModelFactory
import com.honey.duckappretrofit.ui.theme.DuckAppRetrofitTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private lateinit var randomDuckApiService: RandomDuckApiService
    private val viewModel : DuckViewModel by viewModels{
        DuckViewModelFactory(randomDuckApiService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        randomDuckApiService = retrofit.create(RandomDuckApiService::class.java)

        setContent {
            DuckAppRetrofitTheme {
                val connectionManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

                DuckScreen(viewModel = viewModel, connectionManager)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DuckAppRetrofitTheme {
        Greeting("Android")
    }
}