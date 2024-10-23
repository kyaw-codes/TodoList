package com.kyaw.todolist.screens.states

import com.kyaw.todolist.data.Priority
import com.kyaw.todolist.data.Todo

sealed interface TodoEvent {
    data object GetAllTodos: TodoEvent
    data object AddNewButtonTap: TodoEvent
    data class EditTodo(val id: Int): TodoEvent
    data class EditingName(val value: String): TodoEvent
    data class EditingPriority(val priority: Priority): TodoEvent
    data class EditingDeadline(val value: String): TodoEvent
    data class EditingNote(val value: String): TodoEvent
    data object SaveTodo: TodoEvent
    data class ToggleTodo(val data: Todo): TodoEvent
    data object ValidateFormField: TodoEvent
    data class DeleteTodo(val id: Int): TodoEvent
}