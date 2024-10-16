package com.kyaw.todolist.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyaw.todolist.R
import com.kyaw.todolist.data.Priority.Medium
import com.kyaw.todolist.data.Todo
import com.kyaw.todolist.ui.theme.TodoListTheme
import com.kyaw.todolist.ui.theme.onSurfaceVariantLight
import com.kyaw.todolist.ui.theme.primaryLight
import com.kyaw.todolist.ui.theme.secondaryContainerLight
import com.kyaw.todolist.ui.theme.surfaceLight
import com.kyaw.todolist.ui.theme.tertiaryContainerLightMediumContrast
import java.time.Instant
import java.util.Date

@Composable
fun TodoListScreen(
    onTapCreate: () -> Unit, modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar() },
        bottomBar = { BottomBar(onTapCreate = onTapCreate) },
        containerColor = secondaryContainerLight
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            TodoSection(title = "Todo")

            TodoSection(title = "Completed")
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

@Composable
private fun BottomBar(
    onTapCreate: () -> Unit
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

        TextButton(colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = tertiaryContainerLightMediumContrast
        ), onClick = {}) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "All",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif
                )
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "All",
                    tint = tertiaryContainerLightMediumContrast,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun TodoSection(modifier: Modifier = Modifier, title: String) {
    Column(modifier = modifier) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            color = onSurfaceVariantLight
        )

        Spacer(modifier = Modifier.height(4.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items((0..<10).map { it }) {
                TodoItem(
                    todo = Todo(
                        id = 1,
                        name = "Todo 1",
                        priority = Medium,
                        note = "Some note",
                        finished = false,
                        deadline = Date.from(Instant.now())
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Composable
fun TodoItem(modifier: Modifier = Modifier, todo: Todo) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(12.dp).alpha(if (todo.finished) 0.4f else 1f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                if (todo.finished)
                    R.drawable.baseline_check_circle_24
                else
                    R.drawable.baseline_radio_button_unchecked_24
            ),
            contentDescription = "check",
            modifier = Modifier.size(18.dp),
            tint = primaryLight
        )
        Spacer(Modifier.width(4.dp))
        Text(
            todo.name,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = primaryLight,
            style = TextStyle.Default.copy(
                textDecoration = if (todo.finished) TextDecoration.LineThrough else TextDecoration.None
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TodoListScreenPreview() {
    TodoListTheme {
        TodoListScreen({})
    }
}