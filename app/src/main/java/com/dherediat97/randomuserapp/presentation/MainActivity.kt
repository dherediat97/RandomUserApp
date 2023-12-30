package com.dherediat97.randomuserapp.presentation

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dherediat97.randomuserapp.presentation.peopledetail.PersonDetailScreen
import com.dherediat97.randomuserapp.presentation.peoplelist.PersonListScreen
import com.dherediat97.randomuserapp.presentation.peoplelist.PersonListViewModel
import com.dherediat97.randomuserapp.ui.theme.RandomUserAppTheme
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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val ctx = applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        Configuration.getInstance().userAgentValue = "RandomUserApp"
    }
}

@Composable
fun NavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Main.createRoute()
) {
    val myViewModel: PersonListViewModel = viewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavArgs.Main.key) {
            PersonListScreen(
                viewModel = myViewModel,
                onNavigatePerson = { person ->
                    navController.navigate(Screen.Detail.createRoute(person))
                },
            )
        }
        composable(
            NavArgs.PersonDetail.key,
            arguments = listOf(navArgument("personEmail") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            PersonDetailScreen(
                viewModel = myViewModel,
                personEmail = backStackEntry.arguments?.getString("personEmail")!!,
                backToList = {
                    navController.navigateUp()
                })
        }
    }
}


@Composable
fun TopAppBar() {
    val navController = rememberNavController()

    NavHost(navController = navController)
}