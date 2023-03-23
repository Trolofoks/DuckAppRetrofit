package com.honey.duckappretrofit.ui.screen.duck

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun DuckScreen(viewModel: DuckViewModel, connectivityManager: ConnectivityManager) {
 

    val duck = viewModel.duck.collectAsState(initial = null)

    val show = remember { mutableStateOf("empty")}

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Test Duck Api")
            if (show.value == "image"){
                Box(modifier = Modifier.size(256.dp), contentAlignment = Alignment.Center){
                    CircularProgressIndicator(modifier = Modifier.size(128.dp))
                    duck.value?.let {
                        Image(
                            painter = rememberAsyncImagePainter(model = duck.value!!.url),
                            contentDescription = "Quack",
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .size(256.dp)
                        )
                    }
                }
            } else if (show.value == "error"){
                Text(text = "No Internet", style = MaterialTheme.typography.headlineMedium)
            }
            Button(onClick = {

                val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                val hasInternetAccess = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true


                if (hasInternetAccess){
                    viewModel.getDuck()
                    show.value = "image"
                } else {
                    show.value = "error"
                }

            }) {
                Text(text = "Get Duck")
            }
        }
    }
}