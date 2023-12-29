package com.dherediat97.randomuserapp.presentation

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dherediat97.randomuserapp.presentation.peopledetail.PersonDetailScreen
import com.dherediat97.randomuserapp.presentation.peopledetail.PersonDetailViewModel
import com.dherediat97.randomuserapp.presentation.peoplelist.PersonListScreen
import com.dherediat97.randomuserapp.ui.theme.Black
import com.dherediat97.randomuserapp.ui.theme.RandomUserAppTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoinContext {
                RandomUserAppTheme {
                    TopAppBar()
                }
            }
        }
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = "RandomUserApp"
    }
}

@Composable
fun NavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Main.createRoute(),
    detailViewModel: PersonDetailViewModel = koinViewModel(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavArgs.Main.key) {
            PersonListScreen(
                onNavigatePerson = {
                    detailViewModel.addPerson(it)
                    navController.navigate(Screen.Detail.createRoute()) {
                        popUpTo("/") {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(NavArgs.PersonDetail.key) {
            PersonDetailScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    val navController = rememberNavController()

    var showTopBar by rememberSaveable { mutableStateOf(true) }

    showTopBar = when (navController.currentBackStackEntry?.destination?.route) {
        NavArgs.Main.key -> true
        NavArgs.PersonDetail.key -> false
        else -> true
    }

    Scaffold(
        topBar = {
            if (showTopBar) {
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
                            navController.navigateUp()
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
                        Text("CONTACTOS", fontWeight = FontWeight.Bold)
                    },
                )
            }
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), color = MaterialTheme.colorScheme.background
        ) {
            NavHost(navController)
        }
    }
}