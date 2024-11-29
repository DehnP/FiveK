package com.peterd.fivek.presentation.data

import android.content.Context
import com.peterd.fivek.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.lang.String.format

const val WARMUP_DURATION_MILLIS = 5 * 60 * 1000L
const val COOLDOWN_DURATION_MILLIS = 5 * 60 * 1000L

@Serializable
data class WorkoutSegmentData(
    val type: String,
    val duration: Long
)

@Serializable
data class WorkoutData(
    val week: Int,
    val run: Int,
    val segments: List<WorkoutSegmentData>
)


fun loadWorkoutDataFromResources(context: Context): List<WorkoutData> {
    val inputStream = context.resources.openRawResource(R.raw.workout_data)
    val jsonString = inputStream.bufferedReader().use { it.readText() }
    return Json.decodeFromString<List<WorkoutData>>(jsonString)
}

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

val week5Run1 = Workout(
    segments = listOf(
        WorkoutSegment(SegmentType.WARMUP, WARMUP_DURATION_MILLIS),
        WorkoutSegment(SegmentType.RUN, 5 * 60 * 1000),
        WorkoutSegment(SegmentType.WALK, 3 * 60 * 1000),
        WorkoutSegment(SegmentType.RUN, 5 * 60 * 1000),
        WorkoutSegment(SegmentType.WALK, 3 * 60 * 1000),
        WorkoutSegment(SegmentType.RUN, 5 * 60 * 1000),
        WorkoutSegment(SegmentType.COOLDOWN, COOLDOWN_DURATION_MILLIS)
    )
)
private val segmentTypeMap = mapOf(
    "RUN" to SegmentType.RUN,
    "WALK" to SegmentType.WALK
)

fun buildSegmentFromData(workoutSegmentData: WorkoutSegmentData): WorkoutSegment {
    val type = segmentTypeMap[workoutSegmentData.type]
        ?: throw IllegalArgumentException("Invalid segment type: ${workoutSegmentData.type}")
    return WorkoutSegment(type, workoutSegmentData.duration * 1000)
}

fun buildWorkoutFromData(workoutData: WorkoutData): Workout {
    val segments = workoutData.segments.map {
        buildSegmentFromData(it)
    }.toMutableList()

    segments.add(0, WorkoutSegment(SegmentType.WARMUP, WARMUP_DURATION_MILLIS))
    segments.add(WorkoutSegment(SegmentType.COOLDOWN, COOLDOWN_DURATION_MILLIS))

    return Workout(
        segments
    )
}

fun findWorkoutData(workoutsData: List<WorkoutData>, week: Int, run: Int): WorkoutData {
    return workoutsData.firstOrNull { it.week == week && it.run == run }
        ?: throw IllegalArgumentException("No workout found for week $week, run $run")
}

fun getWorkoutFromIndices(weekIndex: Int, runIndex: Int, workoutsData: List<WorkoutData>): Workout {
    if (weekIndex < 1 || runIndex < 1) {
        throw IllegalArgumentException("Invalid indices")
    }

    val workoutData = findWorkoutData(workoutsData, weekIndex, runIndex)
    return buildWorkoutFromData(workoutData)
}

fun getCurrentSegmentType(workout: Workout, elapsedTime: Long): String {
    if (elapsedTime >= workout.length) {
        return DisplayText.DONE
    }
    var accumulatedSegmentTime: Long = 0

    for (segment in workout.segments) {
        accumulatedSegmentTime += segment.duration
        if (elapsedTime <= accumulatedSegmentTime) {
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

fun formatTimeLeft(time: Long): String {
    val minutes = time / (60 * 1000)
    val seconds = (time % (60 * 1000)) / 1000

    val formattedMinutes = if (minutes.toInt() == 0) "0" else minutes.toString()
    when (seconds) {
        0L -> return "$formattedMinutes:00"
        in 1..9 -> return "$formattedMinutes:0$seconds"
    }
    val formattedSeconds = if (seconds.toInt() == 0) "00" else seconds.toString()

    return "$formattedMinutes:$formattedSeconds"
}

fun getTotalTimeLeft(workout: Workout, elapsedTime: Long): String {
    if (elapsedTime >= workout.length) {
        return "0:00"
    }
    if (elapsedTime < 0) {
        throw IllegalArgumentException("Elapsed time cannot be negative")
    }
    val timeLeft = workout.length - elapsedTime

    return formatTimeLeft(timeLeft)
}

fun getCurrentSegmentTimeLeft(workout: Workout, elapsedTime: Long): String {
    var accumulatedSegmentTime: Long = 0

    for (segment in workout.segments) {
        accumulatedSegmentTime += segment.duration
        if (elapsedTime < accumulatedSegmentTime) {
            val segmentTimeLeft = accumulatedSegmentTime - elapsedTime
            return formatTimeLeft(segmentTimeLeft)
        }
    }
    return "0:00"
}

fun countElapsedSegments(workout: Workout, elapsedTime: Long): Int {
    var accumulatedSegmentTime: Long = 0
    var segmentCount = 0

    for (segment in workout.segments) {
        segmentCount++
        accumulatedSegmentTime += segment.duration
        if (elapsedTime < accumulatedSegmentTime) {
            return segmentCount - 1
        }
    }
    return segmentCount
}

fun shouldVibrate(workout: Workout, elapsedTime: Long, vibrationCount: Int): Boolean {
    val elapsedSegments = countElapsedSegments(workout, elapsedTime)
    return vibrationCount < elapsedSegments
}