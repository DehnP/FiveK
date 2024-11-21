package com.peterd.fivek.presentation.views

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.NavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.peterd.fivek.presentation.data.WorkoutData
import com.peterd.fivek.presentation.data.loadWorkoutDataFromResources
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RunScreenTest {
    private lateinit var workoutsData: List<WorkoutData>

    @Before
    fun setUp() {
        // Initialize workoutsData using InstrumentationRegistry
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        workoutsData = loadWorkoutDataFromResources(context)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `SHOULD display Week X Run Y and GO button on startup`() {
        composeTestRule.setContent {
            RunScreen(
                weekIndex = 5,
                runIndex = 1,
                workoutsData = workoutsData
            )
        }

        composeTestRule.onNodeWithText("Week 5 Run 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("GO!").assertIsDisplayed()

    }

    @Test
    fun `SHOULD show Pause when GO button is clicked`() {
        composeTestRule.setContent {
            RunScreen(
                weekIndex = 5,
                runIndex = 1,
                workoutsData = workoutsData
            )
        }

        composeTestRule.onNodeWithText("GO!").performClick()

        composeTestRule.onNodeWithText("Pause").assertIsDisplayed()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `SHOULD start counting down when GO button is clicked`() = runTest {
        composeTestRule.setContent {
            RunScreen(
                weekIndex = 5,
                runIndex = 1,
                workoutsData = workoutsData
            )
        }
        composeTestRule.onNodeWithText("GO!").performClick()
        delay(1000)
        runCurrent()
        composeTestRule.onRoot().printToLog("RunScreenTest")
        composeTestRule.onNodeWithText("30:59").assertIsDisplayed()
    }
}