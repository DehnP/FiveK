package com.peterd.fivek.presentation.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.peterd.fivek.presentation.MainActivity
import com.peterd.fivek.presentation.composables.RunDial
import com.peterd.fivek.presentation.data.getWorkoutFromIndices
import com.peterd.fivek.presentation.data.week5Run1
import kotlinx.coroutines.delay

@Composable
fun RunScreen(weekIndex: Int, runIndex: Int, navController: NavController) {
    val workout = getWorkoutFromIndices(weekIndex, runIndex, MainActivity.workoutsData)
    // console log workout object
    Log.d("RunScreen", "Workout: $workout")
    Log.d("RunScreen", "WorkoutLength:" + workout.length)
    var elapsedTime by remember {
        mutableLongStateOf(0)
    }
    val timeLeft = workout.length
    var isRunning by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = isRunning) {
        if (isRunning) {
            while (elapsedTime < timeLeft) {
                delay(1000)
                elapsedTime += 1000
            }
        }
    }
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
                RunDial(workout = workout, elapsedTime)
                Button(
                    onClick = { isRunning = !isRunning }
                ) { Text(if (isRunning) "Pause" else "GO!") }
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
