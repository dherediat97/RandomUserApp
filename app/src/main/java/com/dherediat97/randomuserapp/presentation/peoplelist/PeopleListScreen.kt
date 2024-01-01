package com.dherediat97.randomuserapp.presentation.peoplelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.presentation.peoplecard.PersonCardItem
import com.dherediat97.randomuserapp.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonListScreen(
    paddingValues: PaddingValues = PaddingValues(),
    viewModel: PersonListViewModel,
    onNavigatePerson: (Person) -> Unit,
    onFilterPerson: () -> Unit,
) {
    val data by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState.canScrollForward) {
        if (data.personList.size <= viewModel.MAX_RESULTS_API_SIZE)
            viewModel.fetchMultiplePersons()
    }

    Scaffold(modifier = Modifier.padding(top = 24.dp), topBar = {
        TopAppBar(actions = {
            IconButton(modifier = Modifier.testTag("filterOption"), onClick = {
                onFilterPerson()
            }) {
                Icon(
                    Icons.Rounded.MoreVert,
                    contentDescription = "icon options menu",
                    tint = Black
                )
            }
        }, navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    Icons.Rounded.ArrowBack,
                    contentDescription = "icon back button",
                    tint = Black
                )
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text("CONTACTOS", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        })
    }, content = { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding() - 20.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            if (!data.isLoading) {
                val items by remember { mutableStateOf(data.personList) }

                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .testTag("listPerson"),
                    state = listState,
                    contentPadding = PaddingValues(
                        start = 24.dp,
                        top = paddingValues.calculateTopPadding(),
                        end = 24.dp,
                        bottom = 24.dp
                    ),
                    content = {
                        items(items) { person ->
                            PersonCardItem(person = person, onNavigatePerson = onNavigatePerson)
                        }
                    })
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().testTag("loadingProgress"),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    })
}