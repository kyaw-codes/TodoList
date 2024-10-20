package com.kyaw.todolist.repository

import com.kyaw.todolist.data.Todo

interface TodoRepository {
    fun addNew(todo: Todo)
    fun save(todo: Todo)
    fun getAll(): List<Todo>
    fun getById(id: Int): Todo?
    fun toggle(id: Int)
}

