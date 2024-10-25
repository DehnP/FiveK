package com.peterd.fivek.presentation.utils


fun formatTimeLeft(timeLeft: Long): String {
    val minutes = timeLeft / 1000 / 60
    val seconds = timeLeft / 1000 % 60
    val formattedMinutes = if (minutes.toInt() == 0) "0" else minutes.toString()
    val formattedSeconds = if (seconds.toInt() == 0) "00" else seconds.toString()
    return "$formattedMinutes:$formattedSeconds"
}