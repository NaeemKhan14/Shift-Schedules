package com.example.shiftschedules.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shiftschedules.ui.theme.CardPrimary
import com.example.shiftschedules.ui.theme.TextColorLight
import com.example.shiftschedules.ui.theme.CardShape

@Composable
fun StatsCard(
    title: String,
    value: String,
    description: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = CardPrimary
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), // Padding between cards
        shape = CardShape, // Use shape from theme
        colors = CardDefaults.cardColors(containerColor = backgroundColor), // Card background color
        elevation = CardDefaults.cardElevation(8.dp) // Elevation for shadow
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp) // Padding inside the card
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    color = TextColorLight // Title text color from theme
                )
            )
            Spacer(modifier = Modifier.height(8.dp)) // Space between title and value
            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 32.sp,
                    color = TextColorLight // Value text color from theme
                )
            )
            Spacer(modifier = Modifier.height(8.dp)) // Space between value and description
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    color = TextColorLight.copy(alpha = 0.7f) // Faded description text
                )
            )
        }
    }
}
