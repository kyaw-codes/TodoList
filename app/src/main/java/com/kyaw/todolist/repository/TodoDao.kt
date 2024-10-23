package com.kyaw.todolist.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.kyaw.todolist.data.Todo

@Dao
interface TodoDao {
    @Insert
    suspend fun addNew(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Query("SELECT * FROM todo")
    suspend fun getAll(): List<Todo>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getById(id: Int): Todo?

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteById(id: Int)
}