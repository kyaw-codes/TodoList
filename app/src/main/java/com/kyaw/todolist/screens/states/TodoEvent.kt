package com.kyaw.todolist.screens.states

import com.kyaw.todolist.data.Priority

sealed interface TodoEvent {
    data object GetAllTodos: TodoEvent
    data object AddNewButtonTap: TodoEvent
    data class EditingName(val value: String): TodoEvent
    data class EditingPriority(val priority: Priority): TodoEvent
    data class EditingDeadline(val value: String): TodoEvent
    data class EditingNote(val value: String): TodoEvent
    data object SaveTodo: TodoEvent
}