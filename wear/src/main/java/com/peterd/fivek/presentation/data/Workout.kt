package com.peterd.fivek.presentation.data

import java.lang.String.format

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

object DisplayText { // Change to object or add member declarations
    const val WARM_UP = "Warm Up"
    const val RUN = "Run"
    const val WALK = "Walk"
    const val COOL_DOWN = "Cool Down"
    const val DONE = "Done"
}

//Dummy for now
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

fun getCurrentSegmentType(workout: Workout, timeLeft: Long): String {
    if (timeLeft <= 0) {
        return DisplayText.DONE
    }
    var accumulatedSegmentTime: Long = 0

    for (segment in workout.segments.reversed()) {
        accumulatedSegmentTime += segment.duration
        if (timeLeft <= accumulatedSegmentTime) {
            return when (segment.type) {
                SegmentType.WARMUP -> DisplayText.WARM_UP
                SegmentType.RUN -> DisplayText.RUN
                SegmentType.WALK -> DisplayText.WALK
                SegmentType.COOLDOWN -> DisplayText.COOL_DOWN
            }
        }
    }
    return DisplayText.DONE
}

fun getCurrentSegmentTimeLeft(workout: Workout, timeLeft: Long): String {
    var accumulatedSegmentTime: Long = workout.length

    for (segment in workout.segments.reversed()) {
        accumulatedSegmentTime -= segment.duration
        if (timeLeft > accumulatedSegmentTime) {
            val minutes = (timeLeft - accumulatedSegmentTime) / (60 * 1000)
            val seconds = ((timeLeft - accumulatedSegmentTime) % (60 * 1000)) / 1000
            val formattedMinutes = if (minutes.toInt() == 0) "0" else minutes.toString()
            val formattedSeconds = if (seconds.toInt() == 0) "00" else seconds.toString()
            return "$formattedMinutes:$formattedSeconds"
        }
    }
    return "0:00"
}