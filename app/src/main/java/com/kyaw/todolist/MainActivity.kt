package com.kyaw.todolist

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kyaw.todolist.screens.AppNavHostScreen
import com.kyaw.todolist.screens.TodoListScreen
import com.kyaw.todolist.ui.theme.TodoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupScreenConfig()
        setContent {
            TodoListTheme {
                AppNavHostScreen()
            }
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private fun setupScreenConfig() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        enableEdgeToEdge()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    TodoListTheme {
        TodoListScreen({})
    }
}