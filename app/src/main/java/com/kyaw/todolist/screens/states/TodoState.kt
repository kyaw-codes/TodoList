package com.kyaw.todolist.screens.states

import com.kyaw.todolist.data.Todo

data class TodoState(
    val todo: Todo? = null,
    val todoList: List<Todo> = emptyList()
)