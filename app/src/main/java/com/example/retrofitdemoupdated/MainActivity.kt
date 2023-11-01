package com.example.retrofitdemoupdated

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrofitdemoupdated.model.Post
import com.example.retrofitdemoupdated.model.PostItem
import com.example.retrofitdemoupdated.network.ApiService
import com.example.retrofitdemoupdated.ui.theme.RetrofitDemoUpdatedTheme
import com.example.retrofitdemoupdated.util.Constants
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitDemoUpdatedTheme {
                var response by remember { mutableStateOf<Post?>(null) }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val api = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(ApiService::class.java)
                GlobalScope.launch(Dispatchers.IO) {
                 val result = api.getAllPost()
                    response = result

                    Log.d("mains", "onCreate: ${response.toString()}")



                }

                }
                response?.let { HomeScreen(postList = it) }

            }
        }



    }

    @Composable
    private fun HomeScreen(postList:Post) {
        LazyColumn{
         items(postList){posts->
             Column(
                 modifier = Modifier
                     .fillMaxWidth()
                     .fillMaxHeight(),
                 verticalArrangement = Arrangement.Top,
                 horizontalAlignment = Alignment.Start
             ) {
                 Card(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(horizontal = 12.dp, vertical = 6.dp),
                     shape = RoundedCornerShape(10.dp),
                     elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                     colors = CardDefaults.cardColors(
                         containerColor = Color.LightGray,
                         contentColor = Color.Black
                     )
                 ) {
                     Column(
                         verticalArrangement = Arrangement.SpaceBetween,
                         horizontalAlignment = Alignment.Start,
                         modifier = Modifier.padding(10.dp)
                     ) {

                         Text("Title: ${posts.title}",style = MaterialTheme.typography.titleLarge)
                         Spacer(modifier = Modifier.height(10.dp))
                         Text("Id: ${posts.id}", style = MaterialTheme.typography.titleMedium)
                         Spacer(modifier = Modifier.height(10.dp))
                         Text("Body: ${posts.body}", style = MaterialTheme.typography.titleMedium)
                     }
                 }
             }
         }

        }
    }

}


