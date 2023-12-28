package com.dherediat97.randomuserapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dherediat97.randomuserapp.presentation.peoplelist.PersonListScreen
import com.dherediat97.randomuserapp.ui.theme.RandomUserAppTheme
import org.koin.compose.KoinContext
import org.koin.core.component.KoinComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinContext(){
                RandomUserAppTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        PersonListScreen()
                    }
                }
            }

        }
    }
}