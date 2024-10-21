package com.peterd.fivek

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.peterd.fivek.presentation.views.RunSelectorScreen
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RunSelectorScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `SHOULD display run selector on startup`() {
        composeTestRule.setContent {
            val mockNavController = mockk<NavHostController>()
            RunSelectorScreen(navController = mockNavController)
        }
//    composeTestRule.onRoot().printToLog("SEMANTICS DEBUG")

        composeTestRule.onNodeWithText("My Runs").assertIsDisplayed()

        composeTestRule.onNodeWithText("Week 1").assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription("Week 1, Run 1").assertIsDisplayed()
    }

    @Test
    fun `Should navigate to run detail screen when run button is clicked`() {
        composeTestRule.setContent {
            val mockNavController = mockk<NavHostController>()
            RunSelectorScreen(navController = mockNavController)
        }
        composeTestRule.onNodeWithText("3").performClick()

        composeTestRule.onNodeWithText("Week 1, Run 3").assertIsDisplayed()
    }
}
