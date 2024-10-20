package com.kyaw.todolist.routes

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object TodoList: Route

    @Serializable
    data class EditTodo(
        val id: Int? = null
    ): Route
}

