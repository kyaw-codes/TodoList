package com.kyaw.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val priority: Priority = Priority.LOW,
    val deadline: String = "",
    val note: String = "",
    var finished: Boolean = false
) {
    fun isValid(): Boolean = title.isNotBlank() && deadline.isNotBlank()
}
