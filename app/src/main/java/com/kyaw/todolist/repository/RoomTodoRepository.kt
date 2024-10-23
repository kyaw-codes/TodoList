package com.kyaw.todolist.repository

import com.kyaw.todolist.data.Todo

class RoomTodoRepository(private val dao: TodoDao) : TodoRepository {

    override suspend fun addNew(todo: Todo) {
        dao.addNew(todo)
    }

    override suspend fun update(todo: Todo) {
        dao.update(todo)
    }

    override suspend fun getAll(): List<Todo> {
        return dao.getAll()
    }

    override suspend fun getById(id: Int): Todo? {
        return dao.getById(id)
    }

    override suspend fun toggle(id: Int) {
        getById(id)?.let { it ->
            update(it.copy(finished = !it.finished))
        }
    }

    override suspend fun delete(id: Int): List<Todo> {
        dao.deleteById(id)
        return getAll()
    }
}