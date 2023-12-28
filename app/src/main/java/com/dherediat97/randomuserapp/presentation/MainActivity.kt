package com.dherediat97.randomuserapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dherediat97.randomuserapp.presentation.peopledetail.PersonDetailScreen
import com.dherediat97.randomuserapp.presentation.peoplelist.PersonListScreen
import com.dherediat97.randomuserapp.ui.theme.Black
import com.dherediat97.randomuserapp.ui.theme.RandomUserAppTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinContext {
                RandomUserAppTheme {
                    navController = rememberNavController() // Leave this one
//                    SetupNavGraph(navController = navController)
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainTopAppBar()
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar() {
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
                    Icon(
                        Icons.Rounded.ArrowBack,
                        contentDescription = "icon options menu",
                        tint = Black
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("CONTACTOS", fontWeight = FontWeight.Bold)
                }
            )
        },
    ) { innerPadding ->
        PersonListScreen(paddingValues = innerPadding, navController = rememberNavController())
    }
}
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "peopleList"
    ) {
        composable(
            route = "personList"
        ) {
            PersonListScreen(navController = navController)
        }
        composable(
            route ="personDetail"
        ) {
            PersonDetailScreen(navController = navController)
        }
    }
}