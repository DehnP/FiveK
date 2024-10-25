package com.peterd.fivek.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.peterd.fivek.presentation.composables.RunDial
import com.peterd.fivek.presentation.data.week5Run1

@Composable
fun RunScreen(weekIndex: Int, runIndex: Int, navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Week $weekIndex Run $runIndex")
                RunDial(workout = week5Run1)
                Button(
                    onClick = { /* TODO: GO!! */ }
                ) { Text("GO!") }
            }
        }
    }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true)
@Composable
fun RunScreenPreview() = RunScreen(5, 1, rememberSwipeDismissableNavController())

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun RunScreenPreviewLarge() = RunScreen(5, 1, rememberSwipeDismissableNavController())
