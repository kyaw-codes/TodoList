package com.kyaw.todolist

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class TodoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}