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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun DuckScreen(viewModel: DuckViewModel, connectivityManager: ConnectivityManager) {
 

    val duckState = viewModel.duck.collectAsState()

    val show = remember { mutableStateOf("empty")}

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Test Duck Api")
            if (show.value == "image"){
                if (!duckState.value.error.isNullOrEmpty()){
                    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 16.dp)) {
                        Text(text = "response:", style = MaterialTheme.typography.titleSmall, modifier = Modifier.align(Alignment.CenterHorizontally))
                        Text(text = duckState.value.error.toString(), style = MaterialTheme.typography.titleSmall, color = Color.Red)
                    }
                } else Box(modifier = Modifier.size(256.dp), contentAlignment = Alignment.Center){
                    if (duckState.value.loading){
                        CircularProgressIndicator(modifier = Modifier.size(128.dp))
                    }
                    duckState.value.duck?.let {
                        Image(
                            painter = rememberAsyncImagePainter(model = it.url),
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