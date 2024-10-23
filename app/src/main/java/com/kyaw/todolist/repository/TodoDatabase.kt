package com.kyaw.todolist.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kyaw.todolist.data.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun userDao(): TodoDao

    companion object {
        fun getInstance(context: Context): TodoDatabase {
            return Room.databaseBuilder(
                context,
                TodoDatabase::class.java,
                "todo_db"
            ).build()
        }
    }
}