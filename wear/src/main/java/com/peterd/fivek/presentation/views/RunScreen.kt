package com.peterd.fivek.presentation.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController

@Composable
fun RunScreen(weekIndex: Int, runIndex: Int, navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Week $weekIndex Run $runIndex")
            Button(
                onClick = { /* TODO: GO!! */ }
            ) { Text("GO!") }
        }
    }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true)
@Composable
fun RunScreenPreview() = RunScreen(5, 1, rememberSwipeDismissableNavController())

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun RunScreenPreviewLarge() = RunScreen(5, 1, rememberSwipeDismissableNavController())
