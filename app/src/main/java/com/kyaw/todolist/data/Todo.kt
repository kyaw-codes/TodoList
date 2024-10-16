package com.kyaw.todolist.data

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

data class Todo(
    val id: Int,
    val name: String,
    val priority: Priority,
    val deadline: String,
    val note: String,
    var finished: Boolean
) {

}
