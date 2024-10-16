package com.kyaw.todolist.data

import androidx.compose.ui.graphics.Color
import com.kyaw.todolist.ui.theme.errorContainerLightMediumContrast

enum class Priority {
    High, Medium, Low;

    fun color() = when (this) {
        High -> errorContainerLightMediumContrast
        Medium -> Color(0xFFFF9800)
        Low -> Color(0xFF2196F3)
    }

    fun title() = when (this) {
        High -> "High"
        Medium -> "Medium"
        Low -> "Low"
    }
}