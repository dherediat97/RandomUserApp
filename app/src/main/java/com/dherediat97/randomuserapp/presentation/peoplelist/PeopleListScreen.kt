package com.dherediat97.randomuserapp.presentation.peoplelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dherediat97.randomuserapp.ui.theme.Black
import org.koin.androidx.compose.koinViewModel

@Composable
fun PersonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonListViewModel = koinViewModel(),
) {

    LaunchedEffect(Unit) {
        viewModel.fetchMultiplePersons(10)
    }

    val data by viewModel.uiState.collectAsState()


    if (!data.isLoading) {
        val items by remember { mutableStateOf(data.personList) }
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),

            content = {
                items(items) { person ->
                    Row (modifier = Modifier.padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                        AsyncImage(
                            model = person.picture.large,
                            contentDescription = "person thumbnail",
                            modifier = Modifier
                                .height(80.dp)
                                .width(80.dp)
                        )
                        Text(person.name.first, color = Black, textAlign = TextAlign.Center)
                    }
                }
            })
    }
}