package com.kyaw.todolist.data

import androidx.compose.ui.graphics.Color
import com.kyaw.todolist.screens.states.TodoFilterType
import com.kyaw.todolist.ui.theme.errorContainerLightMediumContrast

enum class Priority {
    HIGH, MEDIUM, LOW;

    fun color() = when (this) {
        HIGH -> errorContainerLightMediumContrast
        MEDIUM -> Color(0xFFFF9800)
        LOW -> Color(0xFF2196F3)
    }

    fun title() = when (this) {
        HIGH -> "High"
        MEDIUM -> "Medium"
        LOW -> "Low"
    }

    fun toFilter() = when (this) {
        HIGH -> TodoFilterType.HIGH
        MEDIUM -> TodoFilterType.MEDIUM
        LOW -> TodoFilterType.LOW
    }
}