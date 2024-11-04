package com.peterd.fivek.presentation.views

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class RunSelectorScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `SHOULD display run selector on startup`() {
        composeTestRule.setContent {
            val mockNavController = mockk<NavHostController>(relaxed = true)
            RunSelectorScreen(navController = mockNavController)
        }

        composeTestRule.onNodeWithText("My Runs").assertIsDisplayed()
        composeTestRule.onNodeWithText("Week 1").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Week 1, Run 1").assertIsDisplayed()
    }

    @Test
    fun `Should navigate to run detail screen when run button is clicked`() {
        val mockNavController = mockk<NavHostController>(relaxed = true)
        composeTestRule.setContent {
            RunSelectorScreen(navController = mockNavController)
        }

        composeTestRule.onNodeWithContentDescription("Week 1, Run 3").performClick()

        // Verify navigation using mockNavController
        io.mockk.verify { mockNavController.navigate("week_1_run_3") } // Replace with your actual route
    }
}