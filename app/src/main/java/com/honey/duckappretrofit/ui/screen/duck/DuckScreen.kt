package com.honey.duckappretrofit.ui.screen.duck

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun DuckScreen(viewModel: DuckViewModel, connectivityManager: ConnectivityManager) {
 

    val duckState = viewModel.duckState.collectAsState()
    val duckError = viewModel.error

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
                    duckState.value.duck.let {
                        Image(
                            painter = rememberAsyncImagePainter(model = it!!.url),
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

            rememberCoroutineScope().launch {


            }
            Button(onClick = {

                val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                val hasInternetAccess = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true


                if (true){
                    show.value = "image"
                    viewModel.getDuck()
                } else {
                    show.value = "error"
                }

            }) {
                Text(text = "Get Duck")
            }
        }
    }
}