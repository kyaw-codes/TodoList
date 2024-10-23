package com.kyaw.todolist.data

data class Todo(
    val id: Int = 0,
    val name: String = "",
    val priority: Priority = Priority.Low,
    val deadline: String = "",
    val note: String = "",
    var finished: Boolean = false
) {
    fun isValid(): Boolean = name.isNotBlank() && deadline.isNotBlank() && note.isNotBlank()
}
