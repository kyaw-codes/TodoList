package com.kyaw.todolist

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kyaw.todolist.screens.AppNavHostScreen
import com.kyaw.todolist.screens.states.TodoViewModel
import com.kyaw.todolist.ui.theme.TodoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreenConfig()
        setContent {
            TodoListTheme {
                AppNavHostScreen(viewModel = viewModel(factory = TodoViewModel.Factory))
            }
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setupScreenConfig() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        enableEdgeToEdge()
    }
}