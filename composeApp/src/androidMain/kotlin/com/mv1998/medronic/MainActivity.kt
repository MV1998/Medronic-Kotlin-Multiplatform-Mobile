package com.mv1998.medronic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mv1998.medronic.fruit_list_app.FruitListWithSearchScreen
import com.mv1998.medronic.place_holder_photos_list_app.PlaceholderPhotosListKtorScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PlaceholderPhotosListKtorScreen()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    FruitListWithSearchScreen()
}