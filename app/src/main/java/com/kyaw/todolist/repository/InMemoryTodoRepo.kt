package com.kyaw.todolist.repository

import com.kyaw.todolist.data.Todo

object InMemoryTodoRepo: TodoRepository {
    private val todos = mutableListOf<Todo>()
    
    override fun addNew(todo: Todo) {
        todos.add(
            todo.copy(id = todos.count().plus(1))
        )
    }

    override fun save(todo: Todo) {
        todos
            .indexOfFirst { it.id == todo.id }
            .takeIf { it != -1 }
            ?.let { todos.set(it, todo) }
    }

    override fun getAll(): List<Todo> {
        return todos
    }

    override fun getById(id: Int): Todo? {
        return todos.firstOrNull { it.id == id }
    }

    override fun toggle(id: Int) {
        todos
            .indexOfFirst { it.id == id }
            .takeIf { it != -1 }
            ?.let {
                val item = todos[it]
                todos[it] = item.copy(finished = !item.finished)
            }
    }
}