package com.kyaw.todolist.screens.states

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.kyaw.todolist.data.Priority
import com.kyaw.todolist.data.Todo
import com.kyaw.todolist.di.TodoProviders
import com.kyaw.todolist.repository.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class TodoViewModel(private val repo: TodoRepository) : ViewModel() {

    private var _state = MutableStateFlow(TodoState())
    val state: StateFlow<TodoState> = _state.onStart {
        action(TodoEvent.GetAllTodos)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), TodoState()
    )

    fun action(event: TodoEvent) {
        viewModelScope.launch {
            when (event) {
                TodoEvent.GetAllTodos -> _state.update {
                    TodoState(todoList = repo.getAll())
                }

                TodoEvent.AddNewButtonTap -> _state.update {
                    it.copy(todo = Todo(deadline = run {
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(
                            Date.from(Instant.now())
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
                        it.copy(todo = it.todo?.copy(title = event.value))
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
                        repo.update(it)
                    }
                    action(TodoEvent.GetAllTodos)
                }

                is TodoEvent.ToggleTodo -> {
                    repo.toggle(event.data.id)
                    action(TodoEvent.GetAllTodos)
                }

                is TodoEvent.EditTodo -> repo.getById(event.id)?.let { data ->
                    _state.update {
                        it.copy(
                            todo = data,
                            enableSaveButton = _state.value.todo?.isValid() ?: false
                        )
                    }
                }

                is TodoEvent.ValidateFormField -> {
                    _state.update {
                        it.copy(enableSaveButton = _state.value.todo?.isValid() ?: false)
                    }
                }

                is TodoEvent.DeleteTodo -> {
                    val list = repo.delete(event.id)
                    _state.update {
                        it.copy(todoList = list)
                    }
                }

                is TodoEvent.Filter -> {
                    val filterType = event.type
                    val list = repo.getAll()
                    _state.update { it.copy(filterType = filterType) }
                    when (filterType) {
                        TodoFilterType.HIGH -> {
                            _state.update { state ->
                                state.copy(todoList = list.filter { it.priority == Priority.HIGH })
                            }
                        }

                        TodoFilterType.MEDIUM -> {
                            _state.update { state ->
                                state.copy(todoList = list.filter { it.priority == Priority.MEDIUM })
                            }
                        }

                        TodoFilterType.LOW -> {
                            _state.update { state ->
                                state.copy(todoList = list.filter { it.priority == Priority.LOW })
                            }
                        }

                        TodoFilterType.TODO -> {
                            _state.update { state ->
                                state.copy(todoList = list.filter { !it.finished })
                            }
                        }

                        TodoFilterType.COMPLETED -> {
                            _state.update { state ->
                                state.copy(todoList = list.filter { it.finished })
                            }
                        }

                        TodoFilterType.ALL -> {
                            _state.update {
                                it.copy(todoList = list)
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val providers: TodoProviders =
                    TodoProviders(this[APPLICATION_KEY]!!.applicationContext)
                val repo = providers.repository()

                TodoViewModel(repo)
            }
        }
    }
}