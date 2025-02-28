package com.example.shiftschedules.ui.screens

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.shiftschedules.R
import com.example.shiftschedules.data.repositories.UserPreferences
import com.example.shiftschedules.utils.ScrollableScreen
import com.example.shiftschedules.utils.setAppLocale
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    ScrollableScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsRowWithLabel(
                label = stringResource(R.string.label_name),
                tooltip = stringResource(R.string.tooltip_name),
                icon = R.drawable.id_card
            ) {
                EditableFieldWithEditIcon(hint = stringResource(R.string.hint_name))
            }

            SettingsRowWithLabel(
                label = stringResource(R.string.label_hours_per_week),
                tooltip = stringResource(R.string.tooltip_hours_per_week),
                icon = R.drawable.hourglass_empty
            ) {
                EditableFieldWithEditIcon(
                    hint = stringResource(R.string.hint_hours),
                    isIntOnly = true
                )
            }

            SettingsRowWithLabel(
                label = stringResource(R.string.label_store_location),
                tooltip = stringResource(R.string.tooltip_store_location),
                icon = R.drawable.location_on
            ) {
                EditableFieldWithEditIcon(hint = stringResource(R.string.hint_store_location))
            }

            SettingsRowWithLabel(
                label = stringResource(R.string.label_language),
                tooltip = stringResource(R.string.tooltip_language),
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
    var showTooltip by remember { mutableStateOf(false) }

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
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1, // Prevent overflow
                        overflow = TextOverflow.Ellipsis
                    )
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
            content()
        }
    }
}

@Composable
fun EditableFieldWithEditIcon(hint: String, isIntOnly: Boolean = false) {
    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(if (isIntOnly) "0" else hint) }

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
                    .weight(1f)
                    .padding(end = 8.dp),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                    .padding(bottom = 8.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .then(modifier)
                    .widthIn(max = 200.dp), // Limit tooltip width
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = tooltipText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun LanguageDropdown() {
    val context = LocalContext.current
    val userPreferences = remember { UserPreferences(context) }
    val coroutineScope = rememberCoroutineScope()

    // Observing the selected language from DataStore
    val selectedLanguageFlow = userPreferences.selectedLanguage.collectAsState(initial = "en")

    // Map the selected language to UI text
    val selectedLanguage = when (selectedLanguageFlow.value) {
        "de" -> stringResource(R.string.language_german)
        else -> stringResource(R.string.language_english)
    }

    var expanded by remember { mutableStateOf(false) }

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
                text = { Text(stringResource(R.string.language_english)) },
                onClick = {
                    coroutineScope.launch {
                        userPreferences.setLanguage("en")
                        setAppLocale(context, "en") // Set English locale
                    }
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text(stringResource(R.string.language_german)) },
                onClick = {
                    coroutineScope.launch {
                        userPreferences.setLanguage("de")
                        setAppLocale(context, "de") // Set German locale
                    }
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
    var alarmOffsetText = stringResource(R.string.hint_offset)
    var text by remember { mutableStateOf(alarmOffsetText) }
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
                tooltipText = stringResource(R.string.tooltip_alarm),
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


