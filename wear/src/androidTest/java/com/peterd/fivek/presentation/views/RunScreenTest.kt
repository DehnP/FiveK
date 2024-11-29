package com.peterd.fivek.presentation.views

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import com.peterd.fivek.presentation.data.WorkoutData
import com.peterd.fivek.presentation.data.loadWorkoutDataFromResources
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class RunScreenTest {
    private lateinit var workoutsData: List<WorkoutData>

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val printSemanticsOnFailRule = object : TestWatcher() {
        override fun failed(e: Throwable?, description: Description?) {
            composeTestRule.onRoot().printToLog("RunScreenTest")
        }
    }

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        workoutsData = loadWorkoutDataFromResources(context)

        composeTestRule.setContent {
            RunScreen(
                weekIndex = 5,
                runIndex = 1,
                workoutsData = workoutsData
            )
        }

    }

    @Test
    fun `SHOULD display Week X Run Y and GO button on startup`() {

        composeTestRule.onNodeWithText("Week 5 Run 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("GO!").assertIsDisplayed()
    }

    @Test
    fun `SHOULD show Pause when GO button is clicked`() {

        composeTestRule.onNodeWithText("GO!").performClick()
        composeTestRule.onNodeWithText("Pause").assertIsDisplayed()
    }

    @Test
    fun `SHOULD start counting down and pause correctly`() {

        composeTestRule.onNodeWithText("GO!").performClick()
        composeTestRule.mainClock.advanceTimeBy(1500L)
        composeTestRule.onNodeWithText("30:59").assertIsDisplayed()

        composeTestRule.onNodeWithText("Pause").performClick()
        composeTestRule.mainClock.advanceTimeBy(3000L)
        composeTestRule.onNodeWithText("30:59").assertIsDisplayed()

        composeTestRule.onNodeWithText("GO!").performClick()
        composeTestRule.mainClock.advanceTimeBy(3200L)
        composeTestRule.onNodeWithText("30:56").assertIsDisplayed()

    }
    @Test
    fun `SHOULD vibrate correctly`() {

        composeTestRule.onNodeWithText("GO!").performClick()
        composeTestRule.mainClock.advanceTimeBy(1000 * 60 * 6) // 6 minutes
        composeTestRule.onNodeWithContentDescription("vibrationCount: 1").assertExists()
        composeTestRule.mainClock.advanceTimeBy(1000 * 60 * 5) // 5 minutes
        composeTestRule.onNodeWithContentDescription("vibrationCount: 2").assertExists()


    }
}
