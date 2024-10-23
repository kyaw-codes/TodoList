package com.kyaw.todolist.repository

import com.kyaw.todolist.data.Todo
import kotlin.random.Random

class InMemoryTodoRepository : TodoRepository {
    private val list = mutableListOf<Todo>()

    override suspend fun addNew(todo: Todo) {
        list.add(todo.copy(id = list.size + 1))
    }

    override suspend fun update(todo: Todo) {
        list
            .indexOfFirst { it.id == todo.id }
            .also { list[it] = todo }
    }

    override suspend fun getAll(): List<Todo> {
        return list.toList()
    }

    override suspend fun getById(id: Int): Todo? = list.first { it.id == id }

    override suspend fun toggle(id: Int) {
        val index = list.indexOfFirst { it.id == id }
        val item = list[index]
        list[index] = item.copy(finished = !item.finished)
    }

    override suspend fun delete(todo: Int) {
        list.removeAll { it.id == todo }
    }
}