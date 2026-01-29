package com.mv1998.medronic.place_holder_photos_list_app

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.mv1998.medronic.viewmodels.place_holder_photos_view_model.PlaceholderPhotosViewModel

@Composable
fun PlaceholderPhotosListKtorScreen(
    placeholderPhotosViewModel: PlaceholderPhotosViewModel = viewModel(
      factory = PlaceholderPhotosViewModelAndroidFactory()
    ),
    modifier: Modifier = Modifier) {

    val result = placeholderPhotosViewModel.photosResult.collectAsStateWithLifecycle().value

    Scaffold { paddingValues ->
        Surface(
            modifier
                .background(color = Color.White)
                .padding(paddingValues)
        ) {
            if (result.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = Color.Blue)
                        Text("Loading...")
                    }
                }
            }else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(result.photos) {
                        Column {
                            ListItem(
                                trailingContent = {
                                    AsyncImage("https://picsum.photos/200/300", contentDescription =  null,
                                        contentScale = ContentScale.Crop,
                                        modifier =
                                        modifier.size(80.dp)
                                            .clip(shape = CircleShape))
                                },
                                leadingContent = {
                                    Box(
                                        modifier = Modifier
                                            .width(48.dp)
                                            .height(48.dp)
                                            .background(
                                                color = Color.Blue,
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                    ) {
                                        Text(it.id.toString(), style = TextStyle(color = Color.White), modifier = modifier.align(alignment = Alignment.Center))
                                    }
                                },
                                headlineContent = {
                                Text(it.title)
                            }, supportingContent = {Text(it.url)})
                            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}