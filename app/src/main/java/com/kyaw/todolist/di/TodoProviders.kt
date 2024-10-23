package com.kyaw.todolist.di

import android.content.Context
import com.kyaw.todolist.repository.InMemoryTodoRepository
import com.kyaw.todolist.repository.RoomTodoRepository
import com.kyaw.todolist.repository.TodoDatabase
import com.kyaw.todolist.repository.TodoRepository

class TodoProviders(context: Context) {

    private val db by lazy {
        TodoDatabase.getInstance(context)
    }

    private val repository by lazy {
        RoomTodoRepository(db.userDao())
    }

    private val inMemoryRepository by lazy {
        InMemoryTodoRepository()
    }

    fun repository() : TodoRepository = repository
}