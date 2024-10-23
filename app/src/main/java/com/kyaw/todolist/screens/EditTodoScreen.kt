package com.kyaw.todolist.screens

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyaw.todolist.data.Priority
import com.kyaw.todolist.screens.states.TodoEvent
import com.kyaw.todolist.screens.states.TodoState
import com.kyaw.todolist.screens.states.asState
import com.kyaw.todolist.ui.theme.TodoListTheme
import com.kyaw.todolist.ui.theme.onSurfaceVariantLight
import com.kyaw.todolist.ui.theme.outlineLight
import com.kyaw.todolist.ui.theme.outlineVariantLight
import com.kyaw.todolist.ui.theme.primaryLight
import com.kyaw.todolist.ui.theme.scrimLight
import com.kyaw.todolist.ui.theme.surfaceContainerHighLight
import com.kyaw.todolist.ui.theme.surfaceLight
import kotlinx.coroutines.delay
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

@Composable
fun EditTodoScreen(
    id: Int?,
    modifier: Modifier = Modifier,
    state: State<TodoState>,
    onAction: (TodoEvent) -> Unit,
    onBack: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Scaffold(modifier = modifier, containerColor = surfaceLight, topBar = {
        AppBar(
            onBack = {
                focusManager.clearFocus()
                onBack()
            },
            onSave = {
                onAction(TodoEvent.SaveTodo)
                onBack()
            },
            scrollState = scrollState,
            state = state
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .padding(innerPadding)
        ) {
            TitleTextField(modifier, todoTitle = state.value.todo?.title ?: "") {
                onAction(TodoEvent.EditingName(it))
            }

            PriorityBadge(
                modifier = Modifier.padding(horizontal = 16.dp),
                priority = state.value.todo?.priority ?: Priority.LOW,
                onClick = {
                    onAction(TodoEvent.EditingPriority(it))
                }
            )

            key(state.value.todo?.deadline) {
                Deadline(
                    modifier = Modifier.padding(16.dp),
                    deadline = state.value.todo?.deadline.orEmpty(),
                    onDateSelected = {
                        onAction(TodoEvent.EditingDeadline(it))
                    }
                )
            }
//
            Note(
                modifier = Modifier.padding(16.dp),
                note = state.value.todo?.note ?: "",
                onNoteChange = {
                    onAction(TodoEvent.EditingNote(it))
                }
            )
        }
    }

    LaunchedEffect(Unit) {
        id?.let { onAction(TodoEvent.EditTodo(it)) }
    }
}

@Composable
fun Note(modifier: Modifier = Modifier, note: String, onNoteChange: (String) -> Unit) {
    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = "Note",
                modifier = Modifier.size(16.dp),
                tint = outlineLight
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Note",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = onSurfaceVariantLight
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = note,
            onValueChange = onNoteChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = surfaceContainerHighLight,
                focusedContainerColor = surfaceContainerHighLight,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle.Default.copy(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                color = onSurfaceVariantLight,
                fontSize = 14.sp
            ),
            placeholder = {
                Text(
                    text = "It is a short note",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = outlineVariantLight
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Deadline(
    modifier: Modifier = Modifier,
    deadline: String,
    onDateSelected: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = convertDateToMillis(deadline),
        selectableDates = PresentOrFutureSelectableDates
    )
    var selectedDate by remember { mutableStateOf(deadline) }

    Column(modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.DateRange,
                contentDescription = "deadline",
                modifier = Modifier.size(16.dp),
                tint = outlineLight
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Deadline",
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = onSurfaceVariantLight
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { showDatePicker = true },
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = surfaceContainerHighLight)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = selectedDate,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = onSurfaceVariantLight, // outlineVariantLight
                textAlign = TextAlign.Start
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                             datePickerState.selectedDateMillis?.let {
                                selectedDate = convertMillisToDate(it)
                                 onDateSelected(selectedDate)
                            }
                            showDatePicker = false
                        }) {
                        Text(
                            "Done",
                            color = primaryLight,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily.SansSerif
                        )
                    }
                },
                colors = DatePickerDefaults.colors(
                    containerColor = surfaceLight
                )
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                    colors = DatePickerDefaults.colors(
                        containerColor = surfaceLight,
                        selectedDayContentColor = Color.White,
                        selectedDayContainerColor = primaryLight,
                        todayDateBorderColor = primaryLight,
                        todayContentColor = primaryLight,
                        disabledDayContentColor = outlineVariantLight
                    )
                )
            }
        }
    }
}

fun dateToMilliseconds(dateString: String): Long? {
    return try {
        val instant = Instant.from(LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        instant.toEpochMilli()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun convertMillisToDate(millis: Long): String {
    // Handle UTC Zone format
    val zonedDate = Instant
        .ofEpochMilli(millis)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
    val localDate = zonedDate.atStartOfDay(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(localDate)
}

fun convertDateToMillis(date: String): Long? {
    if (date.isEmpty()) {
        return null
    }

    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.parse(date).time
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PriorityBadge(
    modifier: Modifier = Modifier,
    priority: Priority,
    onClick: (Priority) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        Button(
            onClick = {
                expanded = true
            },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .menuAnchor(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(surfaceContainerHighLight)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(priority.color())
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = priority.title(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily.SansSerif,
                        color = onSurfaceVariantLight
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        Icons.Outlined.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = onSurfaceVariantLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(surfaceLight)
                .fillMaxWidth()
        ) {
            Priority.entries.forEach {
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(it.color())
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = it.title(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontFamily = FontFamily.SansSerif,
                                color = onSurfaceVariantLight
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            if (it == priority) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = "Checkmark icon",
                                    tint = onSurfaceVariantLight
                                )
                            }
                        }
                    },
                    onClick = {
                        onClick(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun TitleTextField(modifier: Modifier, todoTitle: String, onValueChange: (String) -> Unit) {
    val focusRequester = remember { FocusRequester() }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        textStyle = TextStyle.Default.copy(
            color = scrimLight,
            fontSize = 22.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold
        ),
        value = todoTitle,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = "Todo title...",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif,
                color = outlineVariantLight
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = scrimLight,
            unfocusedTextColor = scrimLight,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        )
    )

    LaunchedEffect(Unit) {
        delay(5000)
        focusRequester.requestFocus()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    onBack: () -> Unit = {},
    onSave: () -> Unit,
    scrollState: ScrollState,
    state: State<TodoState>
) {
    androidx.compose.material3.TopAppBar(
        title = {
            Text("")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if (scrollState.value > 0) primaryLight else surfaceLight,
        ),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = if (scrollState.value > 0) Color.White else primaryLight
                )
            }
        },
        actions = {
            TextButton(onClick = onSave, enabled = state.value.enableSaveButton) {
                Text(
                    "Save",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    color = if (scrollState.value > 0) Color.White else primaryLight,
                    modifier = Modifier.alpha(if (state.value.enableSaveButton) 1f else 0.5f)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
object PresentOrFutureSelectableDates : SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= LocalDate.now().year
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun EditTodoScreenPreview() {
    TodoListTheme {
        EditTodoScreen(
            id = null,
            state = TodoState().asState(),
            onAction = {},
            onBack = {}
        )
    }
}