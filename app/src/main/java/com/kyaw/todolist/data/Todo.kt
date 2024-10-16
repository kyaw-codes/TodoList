package com.kyaw.todolist.data

data class Todo(
    val id: Int? = null,
    val name: String = "",
    val priority: Priority = Priority.Low,
    val deadline: String = "",
    val note: String = "",
    var finished: Boolean = false
)
