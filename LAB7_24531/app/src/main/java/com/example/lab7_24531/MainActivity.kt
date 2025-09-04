package com.example.lab7_24531

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab7_24531.ui.theme.LAB7_24531Theme
import Notification
import NotificationType
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import com.google.firebase.events.Event
import generateFakeNotifications

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LAB7_24531Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NotificationsScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen(modifier: Modifier = Modifier) {
    val allNotifications = remember { generateFakeNotifications() }
    var selectedType by remember { mutableStateOf<NotificationType?>(null) }

    val filteredNotifications = when (selectedType) {
        null -> allNotifications
        else -> allNotifications.filter { it.type == selectedType }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            FilterChip(
                selected = selectedType == NotificationType.GENERAL,
                onClick = {
                    selectedType = if (selectedType == NotificationType.GENERAL) null else NotificationType.GENERAL
                },
                label = { Text("Informativas") }
            )
            Spacer(Modifier.width(8.dp))
            FilterChip(
                selected = selectedType == NotificationType.NEW_MEETING,
                onClick = {
                    selectedType = if (selectedType == NotificationType.NEW_MEETING) null else NotificationType.NEW_MEETING
                },
                label = { Text("Capacitaciones") }
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredNotifications) { notif ->
                NotificationItem(notif)
            }
        }
    }
}

@Composable
fun NotificationItem(notification: Notification) {
    val (bgColor, icon, iconColor) = when (notification.type) {
        NotificationType.GENERAL -> Triple(
            MaterialTheme.colorScheme.secondaryContainer,
            Icons.Default.Notifications,
            MaterialTheme.colorScheme.onSecondaryContainer
        )
        NotificationType.NEW_MEETING -> Triple(
            MaterialTheme.colorScheme.primaryContainer,
            Icons.Default.DateRange,
            MaterialTheme.colorScheme.onPrimaryContainer
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = notification.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = notification.body,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = notification.sendAt,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificationsScreen() {
    LAB7_24531Theme {
        NotificationsScreen()
    }
}
