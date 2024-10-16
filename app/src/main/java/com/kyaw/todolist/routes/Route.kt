package com.kyaw.todolist.routes

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object TodoList: Route

    @Serializable
    data object EditTodo: Route
}

