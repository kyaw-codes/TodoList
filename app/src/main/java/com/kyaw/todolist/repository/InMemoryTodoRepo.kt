package com.kyaw.todolist.repository

import com.kyaw.todolist.data.Todo

object InMemoryTodoRepo: TodoRepository {
    private val todos = mutableListOf<Todo>()
    
    override fun addNew(todo: Todo) {
        todos.add(todo)
    }

    override fun getAll(): List<Todo> {
        return todos
    }
}