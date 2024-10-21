package com.peterd.fivek.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.OutlinedCompactButton
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController

@Composable
fun RunSelectorScreen(navController: NavController) {
  val listState = rememberScalingLazyListState()

  Scaffold(
      modifier = Modifier.fillMaxSize(), positionIndicator = { PositionIndicator(listState) }) {
        ScalingLazyColumn(modifier = Modifier.fillMaxSize(), state = listState) {
          item { Text("My Runs") }
          items(9) { weekIndex ->
            Column {
              Text("Week ${weekIndex + 1}")
              for (runIndex in 1..3) {
                RunButton(weekIndex, runIndex)
              }
            }
          }
        }
      }
}

@Composable
fun RunButton(weekIndex: Int, runIndex: Int) {
  OutlinedCompactButton(
      modifier = Modifier.semantics { contentDescription = "Week ${weekIndex + 1}, Run $runIndex" },
      onClick = { /* TODO: Navigate to run detail screen */ },
  ) {
    Text("$runIndex")
  }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true)
@Composable
fun RunSelectorScreenPreview() = RunSelectorScreen(rememberSwipeDismissableNavController())

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun RunSelectorScreenPreviewDark() = RunSelectorScreen(rememberSwipeDismissableNavController())
