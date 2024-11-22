package com.peterd.fivek.presentation.views

import android.content.Context
import android.os.PowerManager
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.peterd.fivek.presentation.MainActivity.Companion.workoutsData
import com.peterd.fivek.presentation.composables.RunDial
import com.peterd.fivek.presentation.data.WorkoutData
import com.peterd.fivek.presentation.data.getWorkoutFromIndices
import kotlinx.coroutines.delay

@Composable
fun RunScreen(
    weekIndex: Int,
    runIndex: Int,
    workoutsData: List<WorkoutData>
) {
    val workout = getWorkoutFromIndices(weekIndex, runIndex, workoutsData)
    Log.d("RunScreen", "Workout: $workout")
    Log.d("RunScreen", "WorkoutLength:" + workout.length)
    var elapsedTime by remember {
        mutableLongStateOf(0)
    }
    var isRunning by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager

    LaunchedEffect(key1 = isRunning) {
        if (isRunning) {
            val wakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "RunScreen:WorkoutActive"
            )
            wakeLock.acquire(40 * 60 * 1000L /*40 minutes*/)
            try {
                while (elapsedTime < workout.length) {
                    delay(1000)
                    elapsedTime += 1000
                }
            } finally {
                wakeLock.release()
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
fun RunScreenPreview() = RunScreen(5, 1, workoutsData)

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun RunScreenPreviewLarge() = RunScreen(5, 1, workoutsData)
