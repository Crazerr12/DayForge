package com.example.dayforge.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dayforge.R

@Composable
fun CategoryHeader(
    isExpand: Boolean,
    onClick: () -> Unit,
    header: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = if (isExpand) R.drawable.arrow_up_24 else R.drawable.arrow_down_24),
                contentDescription = if (isExpand) "hide tasks" else "show tasks"
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = header,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Preview
@Composable
private fun CategoryHeaderPreview() {
    CategoryHeader(isExpand = false, onClick = {}, header = "Work")
}