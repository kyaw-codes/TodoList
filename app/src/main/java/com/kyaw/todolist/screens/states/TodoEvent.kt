package com.kyaw.todolist.screens.states

import com.kyaw.todolist.data.Priority
import com.kyaw.todolist.data.Todo

sealed interface TodoEvent {
    data object GetAllTodos : TodoEvent
    data object AddNewButtonTap : TodoEvent
    data class EditTodo(val id: Int) : TodoEvent
    data class EditingName(val value: String) : TodoEvent
    data class EditingPriority(val priority: Priority) : TodoEvent
    data class EditingDeadline(val value: String) : TodoEvent
    data class EditingNote(val value: String) : TodoEvent
    data object SaveTodo : TodoEvent
    data class ToggleTodo(val data: Todo) : TodoEvent
    data object ValidateFormField : TodoEvent
    data class DeleteTodo(val id: Int) : TodoEvent
    data class Filter(val type: TodoFilterType) : TodoEvent
}

enum class TodoFilterType {
    HIGH, MEDIUM, LOW, TODO, COMPLETED, ALL;

    fun title() = when (this) {
        HIGH -> "High"
        MEDIUM -> "Medium"
        LOW -> "Low"
        TODO -> "Todo"
        COMPLETED -> "Completed"
        ALL -> "All"
    }

    fun emptyStateMessage() = when (this) {
        HIGH -> "Add a new high priority todo item and it will show up here."
        MEDIUM -> "Add a new medium priority todo item and it will show up here."
        LOW -> "Add a new low priority todo item and it will show up here."
        TODO -> "Add a new todo item and it will show up here."
        COMPLETED -> "You haven't completed any tasks yet. Feel free to add a new task, and it will appear here."
        ALL -> "Add a new todo item and it will show up here."
    }
}