package com.kyaw.todolist.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyaw.todolist.R
import com.kyaw.todolist.components.Drag
import com.kyaw.todolist.components.EmptyTodoView
import com.kyaw.todolist.components.HorizontalSwipeRevealLayout
import com.kyaw.todolist.data.Priority
import com.kyaw.todolist.data.Todo
import com.kyaw.todolist.screens.states.TodoEvent
import com.kyaw.todolist.screens.states.TodoFilterType
import com.kyaw.todolist.screens.states.TodoState
import com.kyaw.todolist.screens.states.asState
import com.kyaw.todolist.ui.theme.TodoListTheme
import com.kyaw.todolist.ui.theme.errorContainerDarkMediumContrast
import com.kyaw.todolist.ui.theme.errorContainerLightMediumContrast
import com.kyaw.todolist.ui.theme.onSurfaceVariantLight
import com.kyaw.todolist.ui.theme.outlineLight
import com.kyaw.todolist.ui.theme.primaryLight
import com.kyaw.todolist.ui.theme.primaryLightMediumContrast
import com.kyaw.todolist.ui.theme.secondaryContainerLight
import com.kyaw.todolist.ui.theme.surfaceContainerLowLight
import com.kyaw.todolist.ui.theme.surfaceDimLight
import com.kyaw.todolist.ui.theme.surfaceLight
import com.kyaw.todolist.ui.theme.tertiaryContainerLightMediumContrast
import com.kyaw.todolist.ui.theme.tertiaryLight
import kotlinx.coroutines.launch

@Composable
fun TodoListScreen(
    onTapCreate: () -> Unit,
    onTapItem: (todo: Todo) -> Unit,
    state: State<TodoState>,
    onAction: (TodoEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar() },
        bottomBar = {
            BottomBar(
                filterTitle = state.value.filterType.title(),
                onTapCreate = {
                    onTapCreate()
                    onAction(TodoEvent.AddNewButtonTap)
                },
                onFilter = {
                    onAction(TodoEvent.Filter(it))
                }
            )
        },
        containerColor = secondaryContainerLight
    ) { innerPadding ->

        AnimatedVisibility(
            visible = state.value.todoList.isNotEmpty(),
            enter = slideInVertically() + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -it })
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    )
                    .padding(
                        horizontal = innerPadding.calculateStartPadding(LayoutDirection.Ltr)
                    )
            ) {
                TodoSection(
                    title = state.value.filterType.title(),
                    data = state.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    onToggleItem = {
                        onAction(TodoEvent.ToggleTodo(it))
                    },
                    onEdit = onTapItem,
                    onDelete = {
                        onAction(TodoEvent.DeleteTodo(it.id))
                    }
                )
            }
        }

        AnimatedVisibility(
            visible = state.value.todoList.isEmpty(),
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut()
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    )
                    .padding(
                        horizontal = innerPadding.calculateStartPadding(LayoutDirection.Ltr)
                    ),
                contentAlignment = Alignment.Center
            ) {
                EmptyTodoView(message = state.value.filterType.emptyStateMessage())
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = "Checky",
                fontSize = 30.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.ExtraBold,
                color = primaryLight
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = secondaryContainerLight
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomBar(
    filterTitle: String,
    onTapCreate: () -> Unit,
    onFilter: (TodoFilterType) -> Unit
) {
    Row(
        modifier = Modifier
            .background(surfaceLight)
            .padding(12.dp)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, contentColor = primaryLight
        ), onClick = {
            onTapCreate()
        }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add",
                    tint = primaryLight,
                    modifier = Modifier.size(27.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    "New",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }

        var expanded by remember { mutableStateOf(false) }
        val focusRequester = remember {
            FocusRequester()
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextButton(
                modifier = Modifier
                    .menuAnchor(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = tertiaryContainerLightMediumContrast
                ),
                onClick = { expanded = true }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        filterTitle,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowDown,
                        contentDescription = filterTitle,
                        tint = tertiaryContainerLightMediumContrast,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            ExposedDropdownMenu(
                modifier = Modifier
                    .width(200.dp)
                    .focusRequester(focusRequester)
                    .background(surfaceLight),
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                Column {
                    Priority.entries.forEach {
                        DropdownMenuItem(
                            text = {
                                Row {
                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clip(RoundedCornerShape(5.dp))
                                            .background(it.color())
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = it.title(),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = FontFamily.SansSerif,
                                        color = onSurfaceVariantLight
                                    )
                                }
                            },
                            onClick = {
                                onFilter(it.toFilter())
                                expanded = false
                            }
                        )
                    }
                    DropdownMenuItem(
                        text = {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(errorContainerDarkMediumContrast)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Todo",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily.SansSerif,
                                    color = onSurfaceVariantLight
                                )
                            }
                        },
                        onClick = {
                            onFilter(TodoFilterType.TODO)
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(tertiaryLight)
                                )

                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Completed",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily.SansSerif,
                                    color = onSurfaceVariantLight
                                )
                            }
                        },
                        onClick = {
                            onFilter(TodoFilterType.COMPLETED)
                            expanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = {
                            Row {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(surfaceDimLight)
                                )

                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "All",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = FontFamily.SansSerif,
                                    color = outlineLight
                                )
                            }
                        },
                        onClick = {
                            onFilter(TodoFilterType.ALL)
                            expanded = false
                        }
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoSection(
    title: String,
    data: TodoState,
    modifier: Modifier = Modifier,
    onToggleItem: (todo: Todo) -> Unit,
    onEdit: (todo: Todo) -> Unit,
    onDelete: (todo: Todo) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            color = onSurfaceVariantLight
        )

        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(data.todoList, key = { it.id }) {
                TodoItem(
                    todo = it,
                    onToggleItem = onToggleItem,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    todo: Todo,
    onToggleItem: (todo: Todo) -> Unit,
    onEdit: (todo: Todo) -> Unit,
    onDelete: (todo: Todo) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val coroutineScope = rememberCoroutineScope()

    HorizontalSwipeRevealLayout(
        swipeDistance = 226.dp,
        revealedContent = { state ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryLightMediumContrast
                    ),
                    onClick = {
                        coroutineScope.launch {
                            onEdit(todo)
                            state.animateTo(Drag.Normal)
                        }
                    }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Outlined.Create, contentDescription = "Edit")
                        Text(
                            "Edit",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Button(modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = errorContainerLightMediumContrast
                    ),
                    onClick = {
                        coroutineScope.launch {
                            onDelete(todo)
                            state.animateTo(Drag.Normal)
                        }
                    }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Outlined.Delete, contentDescription = "Delete")
                        Text(
                            "Delete",
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    ) { state ->
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .combinedClickable(
                    interactionSource = interactionSource,
                    indication = LocalIndication.current,
                    onLongClick = {
                        coroutineScope.launch {
                            state.animateTo(Drag.Swiped)
                        }
                    },
                    onClick = {
                        todo.let(onToggleItem)
                    }
                )
                .background(Color.White)
                .padding(12.dp)
                .alpha(if (todo.finished) 0.4f else 1f),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                painter = painterResource(
                    if (todo.finished)
                        R.drawable.baseline_check_circle_24
                    else
                        R.drawable.baseline_radio_button_unchecked_24
                ),
                contentDescription = "check",
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .clickable { onToggleItem(todo) },
                tint = primaryLight
            )
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    todo.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = primaryLight,
                    style = TextStyle.Default.copy(
                        textDecoration = if (todo.finished) TextDecoration.LineThrough else TextDecoration.None
                    )
                )
                Spacer(Modifier.height(8.dp))

                Row {
                    Text(
                        text = todo.priority.title(),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = todo.priority.color(),
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                todo.priority
                                    .color()
                                    .copy(alpha = 0.12f)
                            )
                            .padding(horizontal = 8.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = todo.deadline,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.SansSerif,
                        color = onSurfaceVariantLight,
                        modifier = Modifier.run {
                            clip(RoundedCornerShape(12.dp))
                                .background(surfaceContainerLowLight)
                                .padding(horizontal = 8.dp)
                        }
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TodoListScreenPreview() {
    TodoListTheme {
        TodoListScreen(
            state = TodoState().asState(),
            onAction = {},
            onTapCreate = {},
            onTapItem = {}
        )
    }
}