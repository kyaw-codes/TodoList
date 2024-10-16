package com.kyaw.todolist.screens.states

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kyaw.todolist.data.Todo
import com.kyaw.todolist.repository.InMemoryTodoRepo
import com.kyaw.todolist.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TodoViewModel(private val repo: TodoRepository): ViewModel() {
    private var _state = MutableStateFlow(TodoState())
    val state = _state.asStateFlow()

    fun action(event: TodoEvent) {
        when (event) {
            is TodoEvent.GetAllTodos -> _state.update {
                it.copy(todoList = repo.getAll())
            }
            is TodoEvent.AddNewButtonTap -> _state.update {
                it.copy(todo = Todo())
            }
            is TodoEvent.EditingDeadline -> _state.update {
                it.copy(todo = it.todo?.copy(deadline = event.value))
            }
            is TodoEvent.EditingName -> _state.update {
                it.copy(todo = it.todo?.copy(name = event.value))
            }
            is TodoEvent.EditingNote -> _state.update {
                it.copy(todo = it.todo?.copy(note = event.value))
            }
            is TodoEvent.EditingPriority -> _state.update {
                it.copy(todo = it.todo?.copy(priority = event.priority))
            }
            is TodoEvent.SaveTodo -> _state.value.todo?.let {
                repo.addNew(it)
                action(TodoEvent.GetAllTodos)
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