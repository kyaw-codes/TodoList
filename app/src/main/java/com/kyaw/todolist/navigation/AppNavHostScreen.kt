package com.kyaw.todolist.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kyaw.todolist.navigation.routes.Route
import com.kyaw.todolist.screens.EditTodoScreen
import com.kyaw.todolist.screens.TodoListScreen
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
                state = viewModel.state.collectAsState(),
                onAction = viewModel::action,
                onTapCreate = {
                    navController.navigate(Route.EditTodo())
                },
                onTapItem = {
                    navController.navigate(Route.EditTodo(it.id))
                }
            )
        }

        composable<Route.EditTodo> {
            EditTodoScreen(
                id = it.toRoute<Route.EditTodo>().id,
                state = viewModel.state.collectAsState(),
                onAction = viewModel::action,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}