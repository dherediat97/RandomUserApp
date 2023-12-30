package com.dherediat97.randomuserapp.presentation.peoplelist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.ui.theme.Black
import com.dherediat97.randomuserapp.ui.theme.Grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonListScreen(
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: PersonListViewModel,
    onNavigatePerson: (Person) -> Unit,
) {
    val data by viewModel.uiState.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(listState.canScrollForward) {
        viewModel.fetchMultiplePersons()
    }

    Scaffold(
        modifier = Modifier.padding(top = 24.dp),
        topBar = {
            TopAppBar(
                actions = {
                    Icon(
                        Icons.Rounded.MoreVert,
                        contentDescription = "icon options menu",
                        tint = Black
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                    }) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "icon back button",
                            tint = Black
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("CONTACTOS", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                })
        },
        content = { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = innerPadding.calculateTopPadding() - 20.dp),
                color = MaterialTheme.colorScheme.background
            ) {
                if (!data.isLoading) {
                    val items by remember { mutableStateOf(data.personList) }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        state = listState,
                        contentPadding = PaddingValues(
                            start = 24.dp,
                            top = paddingValues.calculateTopPadding(),
                            end = 24.dp,
                            bottom = paddingValues.calculateBottomPadding()
                        ),

                        content = {
                            items(items) { person ->
                                Row(
                                    modifier = Modifier
                                        .padding(top = 22.dp)
                                        .clickable {
                                            onNavigatePerson(person)
                                        }
                                        .drawBehind {
                                            val y = size.height
                                            drawLine(
                                                Color.LightGray,
                                                Offset(250f, y),
                                                Offset(size.width, y),
                                                1f
                                            )
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    SubcomposeAsyncImage(
                                        model = person.picture.medium,
                                        contentDescription = "person thumbnail",
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .height(80.dp)
                                            .width(80.dp)
                                            .clip(CircleShape)
                                    ) {
                                        val state = painter.state
                                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                            CircularProgressIndicator()
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }

                                    }
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
                                }
                            }
                        })
                } else {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        })
}