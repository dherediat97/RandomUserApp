package com.dherediat97.randomuserapp.presentation.peoplelist

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.printToLog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dherediat97.randomuserapp.presentation.MainActivity
import com.dherediat97.randomuserapp.ui.theme.RandomUserAppTheme
import org.junit.Rule
import org.junit.Test

class PeopleListScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun myComposeUnitTest() {
        composeTestRule.activity.setContent {
            RandomUserAppTheme {
                PersonListScreen(
                    viewModel = viewModel(),
                    onNavigatePerson = {},
                    onFilterPerson = {})
            }
        }
        composeTestRule.onNodeWithText("CONTACTOS")

        Thread.sleep(15000)

        composeTestRule.onNodeWithTag("loadingProgress")
            .assertExists()
            .printToLog("Views")

//        composeTestRule.onNodeWithTag("listPerson", true)
//            .onChildren().assertCountEquals(1)
//            .printToLog("Views")

        Thread.sleep(2500)

//        composeTestRule.onNodeWithTag("filterOption")
//            .performTouchInput { }
//            .printToLog("Views")

        composeTestRule.onRoot(true)
            .printToLog("Views")
    }
}