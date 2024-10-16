package com.kyaw.todolist.data

import java.util.Date

data class Todo(
    val id: Int,
    val name: String,
    val priority: Priority,
    val deadline: Date,
    val note: String,
    var finished: Boolean
)
