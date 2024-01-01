package com.dherediat97.randomuserapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.presentation.peoplecard.PersonCardItem
import com.dherediat97.randomuserapp.presentation.peoplelist.PersonListViewModel
import com.dherediat97.randomuserapp.ui.theme.Black
import com.dherediat97.randomuserapp.ui.theme.Grey
import com.dherediat97.randomuserapp.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    viewModel: PersonListViewModel,
    onNavigatePerson: (Person) -> Unit,
    onPersonList: () -> Unit
) {
    val data by viewModel.searchUiState.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    var searchByNameEnabled by remember { mutableStateOf(true) }
    var searchByEmailEnabled by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.padding(top = 32.dp), topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = onPersonList) {
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
            Text("BUSCAR CONTACTOS", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        })
    }, content = { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 24.dp
                )
        ) {
            Scaffold { paddingValues ->
                SearchBar(
                    colors = SearchBarDefaults.colors(containerColor = White, dividerColor = White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = paddingValues.calculateTopPadding()),
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                        if (searchByNameEnabled) viewModel.filterByName(searchQuery)
                        else viewModel.filterByEmail(searchQuery)
                    },
                    onSearch = {
                        keyboardController?.hide()
                        if (searchByNameEnabled) viewModel.filterByName(searchQuery)
                        else viewModel.filterByEmail(searchQuery)
                    },
                    placeholder = {
                        Text(text = "Buscar contactos...")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            tint = Black,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    tint = Black,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    content = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            FilterChip(selected = searchByNameEnabled, onClick = {
                                searchByNameEnabled = true
                                searchByEmailEnabled = false
                            }, leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountBox,
                                    tint = if (searchByNameEnabled) White else Black,
                                    contentDescription = null
                                )
                            }, label = {
                                Text(
                                    "Por nombre", color =
                                    if (searchByNameEnabled) White else Black
                                )
                            })

                            FilterChip(
                                selected = searchByEmailEnabled,
                                onClick = {
                                    searchByEmailEnabled = true
                                    searchByNameEnabled = false
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        tint = if (searchByEmailEnabled) White else Black,
                                        contentDescription = null
                                    )
                                },
                                label = {
                                    Text(
                                        "Por email",
                                        color = if (searchByEmailEnabled) White else Black
                                    )
                                })
                        }
                        if (!data.isLoading)
                            LazyColumn(modifier = Modifier
                                .padding(
                                    start = 16.dp,
                                    end = 16.dp
                                ), content = {
                                items(
                                    data.persons,
                                ) { person ->
                                    PersonCardItem(
                                        person = person,
                                        onNavigatePerson = onNavigatePerson
                                    )
                                }
                            })
                    },
                    active = true,
                    onActiveChange = {},
                )
            }
        }
    })
}