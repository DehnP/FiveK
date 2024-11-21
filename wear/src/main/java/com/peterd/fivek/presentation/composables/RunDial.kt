package com.peterd.fivek.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.peterd.fivek.presentation.data.Workout
import com.peterd.fivek.presentation.data.getCurrentSegmentTimeLeft
import com.peterd.fivek.presentation.data.getCurrentSegmentType
import com.peterd.fivek.presentation.data.week5Run1
import com.peterd.fivek.presentation.utils.formatTimeLeft

@Composable
fun RunDial(workout: Workout, elapsedTime: Long = workout.length) {

    Column(
        modifier = Modifier.fillMaxSize(0.75f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(getCurrentSegmentType(workout, elapsedTime))
        Text(getCurrentSegmentTimeLeft(workout, elapsedTime), fontSize = 18.sp)

        Spacer(modifier = Modifier.size(12.dp))

        Text("Time Left", fontSize = 10.sp)
        Text(
            formatTimeLeft(workout.length - elapsedTime),
            fontSize = 14.sp
        )
    }
}


@Preview(device = "id:wearos_small_round", showSystemUi = true)
@Composable
fun RunDialPreview() = RunDial(week5Run1)

@Preview(device = "id:wearos_large_round", showSystemUi = true)
@Composable
fun RunDialPreviewLarge() = RunDial(week5Run1)
