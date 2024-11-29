package com.peterd.fivek.presentation.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test


private val week5Run1Data = WorkoutData(
    week = 5,
    run = 1,
    segments = listOf(
        WorkoutSegmentData(
            type = "RUN",
            duration = 300
        ),
        WorkoutSegmentData(
            type = "WALK",
            duration = 180
        ),
        WorkoutSegmentData(
            type = "RUN",
            duration = 300
        ),
        WorkoutSegmentData(
            type = "WALK",
            duration = 180
        ),
        WorkoutSegmentData(
            type = "RUN",
            duration = 300
        ),
    )
)

private val week5Run2Data = WorkoutData(
    week = 5,
    run = 2,
    segments = listOf(
        WorkoutSegmentData(
            type = "RUN",
            duration = 680
        ),
        WorkoutSegmentData(
            type = "WALK",
            duration = 300
        ),
        WorkoutSegmentData(
            type = "RUN",
            duration = 680
        ),
    )
)

private val week9Run3Data = WorkoutData(
    week = 9,
    run = 3,
    segments = listOf(
        WorkoutSegmentData(
            type = "RUN",
            duration = 1800
        )
    )
)

val mockWorkoutsData =
    listOf(
        week5Run1Data,
        week5Run2Data,
        week9Run3Data
    )


class BuildSegmentFromData {
    @Test
    fun `SHOULD throw exception given invalid segment type`() {
        val workoutSegmentData = WorkoutSegmentData(
            type = "INVALID",
            duration = 600
        )
        assertThrows(IllegalArgumentException::class.java) {
            buildSegmentFromData(workoutSegmentData)
        }
    }

    @Test
    fun `SHOULD return RUN segment given RUN data`() {
        val workoutSegmentData = WorkoutSegmentData(
            type = "RUN",
            duration = 600
        )
        val expected = WorkoutSegment(
            type = SegmentType.RUN,
            duration = 10 * 60 * 1000
        )

        val actual = buildSegmentFromData(workoutSegmentData)

        assertEquals(expected, actual)

    }
}

class BuildWorkoutFromData {
    @Test
    fun `SHOULD build workout given data`() {
        val expected = week5Run1

        val actual = buildWorkoutFromData(week5Run1Data)

        assertEquals(expected, actual)
    }
}

class FindWorkoutData {
    @Test
    fun `SHOULD throw exception given invalid indices`() {
        val weekIndex = 0
        val runIndex = 0
        assertThrows(IllegalArgumentException::class.java) {
            findWorkoutData(workoutsData = mockWorkoutsData, week = weekIndex, run = runIndex)
        }
    }

    @Test
    fun `SHOULD return workout given valid indices`() {
        val weekIndex = 5
        val runIndex = 1
        val expected = week5Run1Data

        val actual =
            findWorkoutData(workoutsData = mockWorkoutsData, week = weekIndex, run = runIndex)

        assertEquals(expected, actual)
    }
}

class GetWorkoutFromIndicesTest {
    @Test
    fun `SHOULD throw exception given invalid indices`() {
        val weekIndex = 0
        val runIndex = 0

        assertThrows(IllegalArgumentException::class.java) {
            getWorkoutFromIndices(weekIndex, runIndex, workoutsData = mockWorkoutsData)
        }
    }

    @Test
    fun `SHOULD return workout given valid indices`() {
        val weekIndex = 5
        val runIndex = 1

        val expected = week5Run1

        val actual = getWorkoutFromIndices(weekIndex, runIndex, workoutsData = mockWorkoutsData)

        assertEquals(expected, actual)
    }
}

class GetCurrentSegmentTypeTest {
    @Test
    fun `SHOULD return Done given 0 time left`() {
        val workout = week5Run1
        val elapsedTime: Long = 0
        val expected = "Warm Up"

        val actual = getCurrentSegmentType(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return Cool Down given 2 minutes left`() {
        val workout = week5Run1
        val elapsedTime = week5Run1.length - 1000L
        val expected = "Cool Down"

        val actual = getCurrentSegmentType(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return Warm Up given Full time left`() {
        val workout = week5Run1
        val elapsedTime: Long = 0
        val expected = "Warm Up"

        val actual = getCurrentSegmentType(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return Run given 26 minutes left`() {
        val workout = week5Run1
        val elapsedTime: Long = 6 * 60 * 1000
        val expected = "Run"

        val actual = getCurrentSegmentType(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return Walk given 11 minutes left`() {
        val workout = week5Run1
        val elapsedTime: Long = 11 * 60 * 1000
        val expected = "Walk"

        val actual = getCurrentSegmentType(workout, elapsedTime)

        assertEquals(expected, actual)
    }
}

class FormatTimeTest {
    @Test
    fun `SHOULD return 00 00 given 0`() {
        val time = 0L
        val expected = "0:00"

        val actual = formatTimeLeft(time)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 01 00 given 60`() {
        val time = 60 * 1000L
        val expected = "1:00"

        val actual = formatTimeLeft(time)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 00 06 given 60000`() {
        val time = 6 * 1000L
        val expected = "0:06"

        val actual = formatTimeLeft(time)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 26 08 given `() {
        val time = 8 * 1000 + 26 * 60 * 1000L
        val expected = "26:08"

        val actual = formatTimeLeft(time)

        assertEquals(expected, actual)
    }
}

class GetTotalTimeLeftTest {
    @Test
    fun `SHOULD return 31 00 given 0 elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = 0
        val expected = "31:00"

        val actual = getTotalTimeLeft(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 30 00 given 1 minute elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = 60 * 1000
        val expected = "30:00"

        val actual = getTotalTimeLeft(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 0 00 given full elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = workout.length
        val expected = "0:00"

        val actual = getTotalTimeLeft(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 0 00 given exceeding full elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = 100 * 60 * 1000
        val expected = "0:00"

        val actual = getTotalTimeLeft(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD throw illegal argument exception given negative elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = -1 * 60 * 1000

        assertThrows(IllegalArgumentException::class.java) {
            getTotalTimeLeft(workout, elapsedTime)
        }
    }

}

class GetCurrentSegmentTimeLeftTest {
    @Test
    fun `SHOULD return 5 00 given 0 elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = 0
        val expected = "5:00"

        val actual = getCurrentSegmentTimeLeft(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 0 00 given full elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = workout.length
        val expected = "0:00"

        val actual = getCurrentSegmentTimeLeft(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 1 00 given 30 minutes elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = 30 * 60 * 1000
        val expected = "1:00"

        val actual = getCurrentSegmentTimeLeft(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 5 00 given 0 elapsed time, different workout`() {
        val workout = buildWorkoutFromData(week9Run3Data)
        val elapsedTime: Long = 0
        val expected = "5:00"

        val actual = getCurrentSegmentTimeLeft(workout, elapsedTime)

        assertEquals(expected, actual)
    }
}

class CountElapsedSegmentsTest {
    @Test
    fun `SHOULD return 0 given 0 elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = 0
        val expected = 0

        val actual = countElapsedSegments(workout, elapsedTime)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD return 1 given 6 minutes elapsed time`() {
        val workout = week5Run1
        val elapsedTime: Long = 6 * 60 * 1000
        val expected = 1

        val actual = countElapsedSegments(workout, elapsedTime)

        assertEquals(expected, actual)
    }
}

class ShouldVibrateTest {
    @Test
    fun `SHOULD NOT vibrate given 0 vibration count and not surpassed warmup time`() {
        val workout = week5Run1
        val elapsedTime: Long = 1000 * 60 * 4
        val vibrationCount = 0
        val expected = false

        val actual = shouldVibrate(workout, elapsedTime, vibrationCount)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD vibrate given 0 vibration count and surpassed warmup time`() {
        val workout = week5Run1
        val elapsedTime: Long = 1000 * 60 * 5 + 1000 // Warmup + 1 second
        val vibrationCount = 0
        val expected = true

        val actual = shouldVibrate(workout, elapsedTime, vibrationCount)

        assertEquals(expected, actual)
    }

    @Test
    fun `SHOULD NOT vibrate given 1 vibration count and surpassed warmup time`() {
        val workout = week5Run1
        val elapsedTime: Long = 1000 * 60 * 5 + 1000 // Warmup + 1 second
        val vibrationCount = 1
        val expected = false

        val actual = shouldVibrate(workout, elapsedTime, vibrationCount)

        assertEquals(expected, actual)
    }
    @Test
    fun `SHOULD vibrate given 1 vibration count and surpassed first run segment time`() {
        val workout = week5Run1
        val elapsedTime: Long = 1000 * 60 * 10 + 1000 // 10 minutes + 1 second
        val vibrationCount = 1
        val expected = true
        val actual = shouldVibrate(workout, elapsedTime, vibrationCount)

        assertEquals(expected, actual)
    }
}
