package com.kyaw.todolist.screens

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kyaw.todolist.routes.Route
import com.kyaw.todolist.screens.states.TodoViewModel

@Composable
fun AppNavHostScreen(
    viewModel: TodoViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.TodoList,
        enterTransition = {
            slideInHorizontally(
                tween(300),
                initialOffsetX = { it }
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                tween(300),
                initialOffsetX = { -it }
            )
        },
        exitTransition = {
            slideOutHorizontally(
                tween(300),
                targetOffsetX = { -it }
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                tween(300),
                targetOffsetX = { it }
            )
        }
    ) {
        composable<Route.TodoList> {
            TodoListScreen(
                state = viewModel.state,
                onAction = viewModel::action,
                onTapCreate = {
                    navController.navigate(Route.EditTodo)
                }
            )
        }

        composable<Route.EditTodo> {
            EditTodoScreen(
                state = viewModel.state,
                onAction = viewModel::action,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}