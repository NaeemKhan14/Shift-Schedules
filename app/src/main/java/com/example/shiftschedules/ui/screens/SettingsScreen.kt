package com.example.shiftschedules.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.shiftschedules.R
import com.example.shiftschedules.utils.ScrollableScreen

@Composable
fun SettingsScreen() {
    ScrollableScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsRowWithLabel(
                label = "Name",
                tooltip = "Your name on shift schedule",
                icon = R.drawable.id_card
            ) {
                EditableFieldWithEditIcon(hint = "Enter your name")
            }

            SettingsRowWithLabel(
                label = "Hours Per Week",
                tooltip = "Total allowed working hours per week",
                icon = R.drawable.hourglass_empty
            ) {
                EditableFieldWithEditIcon(hint = "0", isIntOnly = true)
            }
            SettingsRowWithLabel(
                label = "Store Location",
                tooltip = "Store location (e.g. Pariser Platz)",
                icon = R.drawable.location_on
            ) {
                EditableFieldWithEditIcon(hint = "Enter store location")
            }
            SettingsRowWithLabel(
                label = "Language",
                tooltip = "Select your preferred language.",
                icon = R.drawable.translate
            ) {
                LanguageDropdown()
            }
            AlarmSwitchField()
        }
    }
}

@Composable
fun SettingsRowWithLabel(
    label: String,
    tooltip: String,
    icon: Int,
    content: @Composable () -> Unit
) {
    var showTooltip by remember { mutableStateOf(false) } // State to control tooltip visibility

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Tooltip",
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { showTooltip = !showTooltip }
                    )
                    Tooltip(
                        tooltipText = tooltip,
                        visible = showTooltip,
                        onDismissRequest = { showTooltip = false }
                    )
                }
            }

            // Editable or static content
            content()
        }
    }
}


@Composable
fun EditableFieldWithEditIcon(hint: String, isIntOnly: Boolean = false) {
    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(if (isIntOnly) "0" else hint) } // Default to "0" for integers

    if (isEditing) {
        TextField(
            value = text,
            onValueChange = { newValue ->
                if (!isIntOnly || newValue.all { it.isDigit() }) {
                    text = newValue
                }
            },
            placeholder = { Text(hint) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardActions = KeyboardActions(onDone = { isEditing = false }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = if (isIntOnly) KeyboardType.Number else KeyboardType.Text
            )
        )
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(1f) // Use remaining space
                    .padding(end = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { isEditing = true }
            )
        }
    }
}



@Composable
fun Tooltip(
    modifier: Modifier = Modifier,
    tooltipText: String,
    visible: Boolean,
    onDismissRequest: () -> Unit
) {
    if (visible) {
        Popup(
            onDismissRequest = onDismissRequest,
            alignment = Alignment.TopEnd
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 8.dp) // Adjust spacing above the icon
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .then(modifier),
                contentAlignment = Alignment.TopCenter // Aligns the tooltip above the icon
            ) {
                Text(
                    text = tooltipText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


@Composable
fun LanguageDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") }

    Box {
        Text(
            text = selectedLanguage,
            modifier = Modifier
                .clickable { expanded = true }
                .padding(8.dp)
                .fillMaxWidth()
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("English") },
                onClick = {
                    selectedLanguage = "English"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("German") },
                onClick = {
                    selectedLanguage = "German"
                    expanded = false
                }
            )
        }
    }
}


@Composable
fun AlarmSwitchField() {
    var isToggleOn by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("Enter offset") }
    var showTooltip by remember { mutableStateOf(false) } // State for tooltip

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Label Row with Toggle and Tooltip
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.notifications),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Alarm", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Tooltip",
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { showTooltip = !showTooltip }
                    )
                }
                Switch(
                    checked = isToggleOn,
                    onCheckedChange = { isToggleOn = it }
                )
            }

            Tooltip(
                tooltipText = "Automatically put an alarm X hours before shift",
                visible = showTooltip,
                onDismissRequest = { showTooltip = false }
            )

            if (isToggleOn) {
                if (isEditing) {
                    EditableFieldWithEditIcon(hint = "0", isIntOnly = true)
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { isEditing = true }
                        )
                    }
                }
            }
        }
    }
}


