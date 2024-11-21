package com.peterd.fivek.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import com.peterd.fivek.presentation.data.week5Run1
import com.peterd.fivek.presentation.views.RunScreen
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test


// For now, just pass in the Week5Run1 directly, then once all data classes are made, do the rest
class RunDialTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `SHOULD display runDial features `() {
        composeTestRule.setContent {
            RunDial(week5Run1, 0L)
        }

        composeTestRule.onNodeWithText("Time Left")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("31:00")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Warm Up")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("5:00")
            .assertIsDisplayed()

    }

    @Test
    fun `SHOULD display correct timeLeft`() {
        composeTestRule.setContent {
            RunDial(week5Run1, 1000)
        }

        composeTestRule.onNodeWithText("30:59")
            .assertIsDisplayed()
    }

    @Test
    fun `SHOULD display Run when given specific timeLeft`() {
        composeTestRule.setContent {
            RunDial(week5Run1, week5Run1.length - (1000 * 60 * 5))
        }

        composeTestRule.onNodeWithText("Run")
            .assertIsDisplayed()

    }
}