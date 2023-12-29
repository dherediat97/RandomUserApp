package com.dherediat97.randomuserapp.presentation.peoplelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.ui.theme.Black
import com.dherediat97.randomuserapp.ui.theme.Grey
import org.koin.androidx.compose.koinViewModel

@Composable
fun PersonListScreen(
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: PersonListViewModel = koinViewModel(),
    onNavigatePerson: (Person) -> Unit,
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
                top = paddingValues.calculateTopPadding(),
                end = 16.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),

            content = {
                items(items) { person ->
                    Row(
                        modifier = Modifier
                            .padding(top = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(person.picture.large)
                                .crossfade(true)
                                .build(),
                            contentDescription = "person thumbnail",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(80.dp)
                                .width(80.dp)
                                .clip(CircleShape)
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .fillMaxSize(0.90f)
                        ) {
                            Text(
                                "${person.name.first} ${person.name.last}",
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Start
                            )
                            Text(
                                person.email, color = Grey,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                            )
                        }
                        IconButton(onClick = {
                            onNavigatePerson(person)
                        }) {
                            Icon(
                                Icons.Rounded.KeyboardArrowRight,
                                tint = Grey,
                                contentDescription = "icon arrow see details people"
                            )
                        }
//                        Divider(color = Grey, thickness = 1.dp)
                    }

                }
            })
    }
}