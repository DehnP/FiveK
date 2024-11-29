package com.peterd.fivek.presentation.views

import android.content.Context
import android.os.Build
import android.os.CombinedVibration
import android.os.PowerManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.peterd.fivek.presentation.MainActivity.Companion.workoutsData
import com.peterd.fivek.presentation.composables.RunDial
import com.peterd.fivek.presentation.data.Workout
import com.peterd.fivek.presentation.data.WorkoutData
import com.peterd.fivek.presentation.data.getCurrentSegmentType
import com.peterd.fivek.presentation.data.getWorkoutFromIndices
import com.peterd.fivek.presentation.data.shouldVibrate
import kotlinx.coroutines.delay

@Composable
fun RunScreen(
    weekIndex: Int,
    runIndex: Int,
    workoutsData: List<WorkoutData>
) {
    var vibrationCount by remember {
        mutableIntStateOf(0)
    }

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

    fun vibrate(workout: Workout, elapsedTime: Long ) {
        val vibrationManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        } else {
            null // Handle for older devices, fallback to Vibrator
        }
        val currentSegmentType = getCurrentSegmentType(workout, elapsedTime)

        val vibrationEffect: VibrationEffect = when (currentSegmentType) {
            "Run" -> {
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK)
            }

            "Walk" -> {
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
            }

            else -> {
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            vibrationManager?.vibrate(CombinedVibration.createParallel(vibrationEffect))
        }
    }

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
                    if (shouldVibrate(workout, elapsedTime, vibrationCount)) {
                        vibrate(workout, elapsedTime)
                        vibrationCount += 1
                    }
                }
            } finally {
                wakeLock.release()
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .semantics {
                contentDescription = "vibrationCount: $vibrationCount"
            },
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
