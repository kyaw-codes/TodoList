package com.kyaw.todolist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyaw.todolist.R
import com.kyaw.todolist.ui.theme.onSurfaceVariantLight
import com.kyaw.todolist.ui.theme.secondaryContainerLightMediumContrast

@Composable
fun EmptyTodoView(modifier: Modifier = Modifier, message: String? = null) {
    Column(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .padding(12.dp)
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .width(150.dp)
                .height(70.dp),
            painter = painterResource(R.drawable.ic_empty_todo),
            contentDescription = "Oops! It's Empty"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Empty todo.",
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            color = onSurfaceVariantLight
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            message ?: "Add a new todo item and it will show up here.",
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            color = secondaryContainerLightMediumContrast,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyTodoViewPreview() {
    EmptyTodoView(modifier = Modifier.width(300.dp))
}