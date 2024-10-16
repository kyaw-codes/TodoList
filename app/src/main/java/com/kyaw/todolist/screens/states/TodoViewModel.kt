package com.kyaw.todolist.screens.states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kyaw.todolist.data.Todo
import com.kyaw.todolist.repository.InMemoryTodoRepo
import com.kyaw.todolist.repository.TodoRepository

class TodoViewModel(private val repo: TodoRepository): ViewModel() {
    var state by mutableStateOf(TodoState())
        private set

    fun action(event: TodoEvent) {
        when (event) {
            is TodoEvent.FetchAll -> state = state.copy(todoList = repo.getAll())
            is TodoEvent.AddNewButtonTap -> state = state.copy(todo = Todo())
            is TodoEvent.EditTodo -> state = state.copy(todo = event.value)
            is TodoEvent.DeadlineEditing -> state = state.copy(
                todo = state.todo?.copy(deadline = event.value)
            )
            is TodoEvent.NameEditing -> state = state.copy(
                todo = state.todo?.copy(name = event.value)
            )
            is TodoEvent.NoteEditing -> state = state.copy(
                todo = state.todo?.copy(note = event.value)
            )
            is TodoEvent.PriorityEditing -> state = state.copy(
                todo = state.todo?.copy(priority = event.priority)
            )
            is TodoEvent.SaveTodo -> {
                state.todo?.let {
                    repo.addNew(it)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TodoViewModel(InMemoryTodoRepo)
            }
        }
    }
}