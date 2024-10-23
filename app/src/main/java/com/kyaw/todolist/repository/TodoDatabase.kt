package com.kyaw.todolist.repository

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Update
import androidx.room.Upsert
import com.kyaw.todolist.data.Priority
import com.kyaw.todolist.data.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun userDao(): TodoDao
}

@Dao
interface TodoDao {
    @Insert
    fun addNew(todo: Todo)

    @Update
    fun save(todo: Todo)

    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getById(id: Int): Todo?
}

class DatabaseProvider(context: Context) {

    private val db : TodoDatabase = Room.databaseBuilder(context)

    fun getTodoDao() : TodoDao {

    }
}