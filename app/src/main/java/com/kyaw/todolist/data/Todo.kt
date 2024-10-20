package com.kyaw.todolist.data

import kotlin.random.Random

data class Todo(
    val id: Int = 0,
    val name: String = "",
    val priority: Priority = Priority.Low,
    val deadline: String = "",
    val note: String = "",
    var finished: Boolean = false
)
