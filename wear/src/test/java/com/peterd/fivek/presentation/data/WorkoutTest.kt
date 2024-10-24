package com.peterd.fivek.presentation.data

import org.junit.Assert.assertEquals
import org.junit.Test

class GetCurrentSegmentTypeTest {
    @Test
    fun `SHOULD return Done given 0 time left`() {
        val workout = week5Run1
        val timeLeft: Long = 0
        val expected = "Done"

        val actual = getCurrentSegmentType(workout, timeLeft)

        assertEquals(expected, actual)
    }
    @Test
    fun `SHOULD return Cool Down given 2 minutes left`() {
        val workout = week5Run1
        val timeLeft: Long = 1 * 60 * 1000
        val expected = "Cool Down"

        val actual = getCurrentSegmentType(workout, timeLeft)

        assertEquals(expected, actual)
    }
    @Test
    fun `SHOULD return Warm Up given Full time left`() {
        val workout = week5Run1
        val timeLeft: Long = week5Run1.length
        val expected = "Warm Up"

        val actual = getCurrentSegmentType(workout, timeLeft)

        assertEquals(expected, actual)
    }
    @Test
    fun `SHOULD return Run given 26 minutes left`() {
        val workout = week5Run1
        val timeLeft: Long = 26 * 60 * 1000
        val expected = "Run"

        val actual = getCurrentSegmentType(workout, timeLeft)

        assertEquals(expected, actual)
    }
    @Test
    fun `SHOULD return Walk given 11 minutes left`() {
        val workout = week5Run1
        val timeLeft: Long = 11 * 60 * 1000
        val expected = "Walk"

        val actual = getCurrentSegmentType(workout, timeLeft)

        assertEquals(expected, actual)
    }
}

class GetCurrentSegmentTimeLeftTest {
    @Test
    fun `SHOULD return 5 00 given full time left`() {
        val workout = week5Run1
        val timeLeft: Long = workout.length
        val expected = "5:00"

        val actual = getCurrentSegmentTimeLeft(workout, timeLeft)

        assertEquals(expected, actual)
    }
    @Test
    fun `SHOULD return 0 00 given 0 time left`() {
        val workout = week5Run1
        val timeLeft: Long = 0
        val expected = "0:00"

        val actual = getCurrentSegmentTimeLeft(workout, timeLeft)

        assertEquals(expected, actual)
    }
    @Test
    fun `SHOULD return 1 00 given 1 minute left`() {
        val workout = week5Run1
        val timeLeft: Long = 1 * 60 * 1000
        val expected = "1:00"

        val actual = getCurrentSegmentTimeLeft(workout, timeLeft)

        assertEquals(expected, actual)
    }
    @Test
    fun `SHOULD return 2 30 given 7 30 time left`() {
        val workout = week5Run1
        val timeLeft: Long = 7 * 60 * 1000 + (30 * 1000)
        val expected = "2:30"

        val actual = getCurrentSegmentTimeLeft(workout, timeLeft)

        assertEquals(expected, actual)
    }
}