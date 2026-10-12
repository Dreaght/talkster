package dev.dreaght.talkster

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fromwau.kortex.wayland.Dialog

@Composable
fun ConfirmQuit(
    onKeep: () -> Unit,
    onQuit: () -> Unit
) {
    MaterialTheme {
        Dialog(
            title = "Are you sure you want to quit?",
            width = 400.dp,
            height = 180.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Are you sure you want to quit?",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Button(onClick = onQuit) {
                        Text("Quit")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = onKeep) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
