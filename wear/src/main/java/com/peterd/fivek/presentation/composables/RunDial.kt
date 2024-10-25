package com.peterd.fivek.presentation.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.peterd.fivek.presentation.data.Workout
import com.peterd.fivek.presentation.data.getCurrentSegmentTimeLeft
import com.peterd.fivek.presentation.data.getCurrentSegmentType
import com.peterd.fivek.presentation.data.week5Run1

fun formatTimeLeft(timeLeft: Long): String {
    val minutes = timeLeft / 1000 / 60
    val seconds = timeLeft / 1000 % 60
    val formattedMinutes = if (minutes.toInt() == 0) "0" else minutes.toString()
    val formattedSeconds = if (seconds.toInt() == 0) "00" else seconds.toString()
    return "$formattedMinutes:$formattedSeconds"
}

@Composable
fun RunDial(workout: Workout = week5Run1) {

    var timeLeft by remember {
        mutableLongStateOf(workout.length)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(getCurrentSegmentType(workout, timeLeft))
                Text(getCurrentSegmentTimeLeft(workout, timeLeft))
            }
        }
        Text("Time Left", fontSize = 14.sp)
        Text(
            formatTimeLeft(timeLeft),
            fontSize = 18.sp
        )
    }
}

@Preview(device = "id:wearos_small_round", showSystemUi = true)
@Composable
fun RunDialPreview() = RunDial()

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun RunDialPreviewLarge() = RunDial()
