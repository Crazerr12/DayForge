package com.example.dayforge.presentation.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.dayforge.presentation.models.UiState

@Composable
fun <T : UiState> CommonScreen(
    state: UiState,
    onSuccess: @Composable (T) -> Unit,
) {
    if (state.loading) {
        Loading()
    } else if (state.error) {
        Error("")
    } else {
        onSuccess(state as T)
    }
}

@Composable
fun Error(
    error: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Snackbar {
            Text(text = error)
        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}