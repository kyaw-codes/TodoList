package com.kyaw.todolist.repository

import com.kyaw.todolist.data.Todo

interface TodoRepository {
    suspend fun addNew(todo: Todo)
    suspend fun update(todo: Todo)
    suspend fun getAll(): List<Todo>
    suspend fun getById(id: Int): Todo?
    suspend fun toggle(id: Int)
    suspend fun delete(todo: Int)
}
