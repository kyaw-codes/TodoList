package com.kyaw.todolist.screens.states

import com.kyaw.todolist.data.Priority
import com.kyaw.todolist.data.Todo

sealed interface TodoEvent {
    data object FetchAll: TodoEvent
    data object AddNewButtonTap: TodoEvent
    data class EditTodo(val value: Todo): TodoEvent
    data class NameEditing(val value: String): TodoEvent
    data class PriorityEditing(val priority: Priority): TodoEvent
    data class DeadlineEditing(val value: String): TodoEvent
    data class NoteEditing(val value: String): TodoEvent
    data object SaveTodo: TodoEvent
}