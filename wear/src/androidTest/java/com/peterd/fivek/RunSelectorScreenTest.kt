package com.peterd.fivek

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.peterd.fivek.presentation.views.RunSelectorScreen
import org.junit.Rule
import org.junit.Test

class RunSelectorScreenTest {
  @get:Rule val composeTestRule = createComposeRule()

  @Test
  fun `SHOULD display run selector on startup`() {
    composeTestRule.setContent { RunSelectorScreen() }
    composeTestRule.onRoot().printToLog("SEMANTICS DEBUG")

    composeTestRule.onNodeWithText("My Runs").assertIsDisplayed()

    composeTestRule.onNodeWithText("Week 1").assertIsDisplayed()

    composeTestRule.onNodeWithContentDescription("Week 1, Run 1").assertIsDisplayed()
  }
}
