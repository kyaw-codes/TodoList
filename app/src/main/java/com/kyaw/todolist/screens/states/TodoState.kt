package com.kyaw.todolist.screens.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.kyaw.todolist.data.Todo
import kotlinx.coroutines.flow.MutableStateFlow

data class TodoState(
    val todo: Todo? = null,
    val todoList: List<Todo> = emptyList(),
    val enableSaveButton: Boolean = false,
    val filterType: TodoFilterType = TodoFilterType.ALL
) : UIState

interface UIState {}

@Composable
fun <T : UIState> T.asState() = MutableStateFlow(this).collectAsState()
