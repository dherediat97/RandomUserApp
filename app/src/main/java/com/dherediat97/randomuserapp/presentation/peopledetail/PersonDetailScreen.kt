package com.dherediat97.randomuserapp.presentation.peopledetail


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.dherediat97.randomuserapp.ui.theme.Black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonDetailScreen(
    navController: NavController
) {

    Scaffold(
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
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "icon options menu",
                            tint = Black
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("David Heredia", fontWeight = FontWeight.Bold)
                }
            )
        },
    ) { innerPadding ->
        Row(Modifier.padding(top = innerPadding.calculateTopPadding())) {
            Text("HOLAAAAAAAAAAA")
        }
    }
}