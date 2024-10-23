package com.kyaw.todolist.screens.states

import android.util.Log
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
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date
import java.util.Locale

class TodoViewModel(private val repo: TodoRepository) : ViewModel() {


    private var _state = MutableStateFlow(TodoState())
    val state = _state.asStateFlow()

    fun action(event: TodoEvent) {
        when (event) {
            TodoEvent.GetAllTodos -> _state.update {
                TodoState(todoList = repo.getAll())
            }

            TodoEvent.AddNewButtonTap -> _state.update {
                it.copy(todo = Todo(deadline = run {
                    LocalDate.now().plusDays(2).format(
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    )
                }))
            }

            is TodoEvent.EditingDeadline -> {
                _state.update {
                    it.copy(todo = it.todo?.copy(deadline = event.value))
                }
                action(TodoEvent.ValidateFormField)
            }

            is TodoEvent.EditingName -> {
                _state.update {
                    it.copy(todo = it.todo?.copy(name = event.value))
                }
                action(TodoEvent.ValidateFormField)
            }

            is TodoEvent.EditingNote -> {
                _state.update {
                    it.copy(todo = it.todo?.copy(note = event.value))
                }
                action(TodoEvent.ValidateFormField)
            }

            is TodoEvent.EditingPriority -> {
                _state.update {
                    it.copy(todo = it.todo?.copy(priority = event.priority))
                }
                action(TodoEvent.ValidateFormField)
            }

            TodoEvent.SaveTodo -> _state.value.todo?.let {
                // id == 0 means a new todo
                if (it.id == 0) {
                    repo.addNew(it)
                } else {
                    repo.save(it)
                }
                action(TodoEvent.GetAllTodos)
            }

            is TodoEvent.ToggleTodo -> {
                repo.toggle(event.data.id)
                action(TodoEvent.GetAllTodos)
            }

            is TodoEvent.EditTodo -> repo.getById(event.id)?.let { data ->
                _state.update { it.copy(todo = data) }
            }

            is TodoEvent.ValidateFormField -> {
                _state.update {
                    it.copy(enableSaveButton = _state.value.todo?.isValid() ?: false)
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