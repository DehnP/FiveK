package com.peterd.fivek.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.peterd.fivek.presentation.views.RunScreen
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.milliseconds

data class Workout(
    val segments: List<WorkoutSegment>,
    val length: Long = segments.sumOf { it.duration }
)

data class WorkoutSegment(
    val type: SegmentType,
    val duration: Long
)

enum class SegmentType {
    WARMUP,
    RUN,
    WALK,
    COOLDOWN
}

val week5Run1 = Workout(
    segments = listOf(
        WorkoutSegment(SegmentType.WARMUP, 5 * 60 * 1000),
        WorkoutSegment(SegmentType.RUN, 5 * 60 * 1000),
        WorkoutSegment(SegmentType.WALK, 3 * 60 * 1000),
        WorkoutSegment(SegmentType.RUN, 5 * 60 * 1000),
        WorkoutSegment(SegmentType.WALK, 3 * 60 * 1000),
        WorkoutSegment(SegmentType.RUN, 5 * 60 * 1000),
        WorkoutSegment(SegmentType.COOLDOWN, 5 * 60 * 1000)
    )
)

@Composable
fun RunDial(workout: Workout = week5Run1) {
    val minutes by remember { derivedStateOf {
        workout.length / 1000 / 60
    }}
    val seconds by remember { derivedStateOf {
        workout.length / 1000 % 60
    }}
    val formattedMinutes = if (minutes.toInt() == 0) "00" else minutes.toString()
    val formattedSeconds = if (seconds.toInt() == 0) "00" else seconds.toString()
    Scaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Time Left")
                Text(
                    "${formattedMinutes}:${formattedSeconds}")
            }
        }
    }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true)
@Composable
fun RunDialPreview() = RunDial()

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun RunDialPreviewLarge() = RunDial()
