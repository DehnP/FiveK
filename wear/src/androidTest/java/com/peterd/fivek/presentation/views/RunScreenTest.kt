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

class RunScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `SHOULD display Week X Run Y and GO button on startup`() {
        composeTestRule.setContent {
            val mockNavController = mockk<NavHostController>(relaxed = true)
            RunScreen(weekIndex = 5, runIndex = 1, navController = mockNavController)
        }

        composeTestRule.onNodeWithText("Week 5 Run 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("GO!").assertIsDisplayed()

    }
}