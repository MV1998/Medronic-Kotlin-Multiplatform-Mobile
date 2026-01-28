package com.mv1998.medronic.fruit_list_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldLabelPosition
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mv1998.medronic.viewmodels.fruit_view_model.FruitViewModel
import com.mv1998.medronic.viewmodels.fruit_view_model.UiAction

@Composable
@Preview
fun FruitListWithSearchScreen(fruitViewModel: FruitViewModel = viewModel(
    factory = FruitViewModelAndroidFactory()
)) {
    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .safeDrawingPadding()
    ) { padding ->
        val uiState = fruitViewModel.fruitState.collectAsStateWithLifecycle().value
        val searchTextFieldState = rememberTextFieldState()

        LaunchedEffect(Unit) {
            snapshotFlow {
                searchTextFieldState.text
            }.collect {
                fruitViewModel.onAction(UiAction.OnQuery(it.toString()))
            }
        }

        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (uiState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.Blue)
                    Text("Loading...")
                }
            }else {
                OutlinedTextField(
                    state = searchTextFieldState,
                    label = {Text("Search Fruit")},
                    labelPosition = TextFieldLabelPosition.Above(),
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.Transparent
                        )
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                )
                LazyColumn(
                    modifier = Modifier.padding(padding)
                        .fillMaxSize()
                ) {
                    items(uiState.list, key = {it}) {
                        Column {
                            Text(it,
                                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp),
                                modifier =
                                    Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                        .padding(10.dp))
                            HorizontalDivider(
                                thickness = 2.dp,
                                color = Color.Gray.copy(alpha = 0.3f)
                            )
                        }
                    }
                }
            }
        }
    }
}