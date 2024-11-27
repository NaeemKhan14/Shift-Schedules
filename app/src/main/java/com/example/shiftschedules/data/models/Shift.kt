package com.example.shiftschedules.data.models

data class Shift(
    val id: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val hoursWorked: Float,
    val description: String
)
