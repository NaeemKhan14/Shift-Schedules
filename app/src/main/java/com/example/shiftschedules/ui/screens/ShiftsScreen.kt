package com.example.shiftschedules.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.shiftschedules.R
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun ShiftsScreen() {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    val shiftsData = remember {
        mutableStateMapOf<LocalDate, List<String>>(
            LocalDate.now() to listOf("09:00 - 17:00"),
            LocalDate.now().minusDays(1) to listOf("10:00 - 14:00")
        )
    }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header: Month and Year Navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = stringResource(R.string.previous_month),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            IconButton(onClick = {
                if (currentMonth.isBefore(YearMonth.now().plusMonths(1))) {
                    currentMonth = currentMonth.plusMonths(1)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_forward),
                    contentDescription = stringResource(R.string.next_month),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Expanded Calendar Grid
        CalendarGrid(
            yearMonth = currentMonth,
            shiftsData = shiftsData,
            onDayClick = { date ->
                selectedDate = date
            }
        )

        // Show Dialog if a date is selected
        selectedDate?.let { date ->
            val hasShift = shiftsData.containsKey(date)
            ShiftDialog(
                date = date,
                hasShift = hasShift,
                shifts = shiftsData[date] ?: emptyList(),
                onDismiss = { selectedDate = null },
                onAddShift = {
                    shiftsData[date] = listOf("09:00 - 17:00") // Example shift added
                    selectedDate = null
                },
                onEditShift = {
                    shiftsData[date] = listOf("10:00 - 18:00") // Example edit
                    selectedDate = null
                },
                onDeleteShift = {
                    shiftsData.remove(date)
                    selectedDate = null
                }
            )
        }
    }
}


@Composable
fun CalendarGrid(
    yearMonth: YearMonth,
    shiftsData: Map<LocalDate, List<String>>,
    onDayClick: (LocalDate) -> Unit
) {
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfWeek = (yearMonth.atDay(1).dayOfWeek.value + 5) % 7
    val totalCells = firstDayOfWeek + daysInMonth
    val rows = (totalCells / 7) + if (totalCells % 7 != 0) 1 else 0

    val bottomBarHeight = 56.dp
    val calendarHeight = Modifier.fillMaxHeight().padding(bottom = bottomBarHeight)

    val dayNames = listOf(
        stringResource(R.string.day_tue),
        stringResource(R.string.day_wed),
        stringResource(R.string.day_thu),
        stringResource(R.string.day_fri),
        stringResource(R.string.day_sat),
        stringResource(R.string.day_sun),
        stringResource(R.string.day_mon),
    )

    Column(
        modifier = calendarHeight,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dayNames.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        for (row in 0 until rows) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (col in 0..6) {
                    val cellIndex = row * 7 + col
                    val day = cellIndex - firstDayOfWeek + 1
                    val date = yearMonth.atDay(day.coerceAtLeast(1).coerceAtMost(daysInMonth))

                    if (cellIndex < firstDayOfWeek || day > daysInMonth) {
                        Box(modifier = Modifier.weight(1f))
                    } else {
                        DayCell(
                            date = date,
                            shifts = shiftsData[date] ?: emptyList(),
                            isToday = date == LocalDate.now(),
                            onClick = { onDayClick(date) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DayCell(
    date: LocalDate,
    shifts: List<String>,
    isToday: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val hasShift = shifts.isNotEmpty()

    Column(
        modifier = modifier
            .padding(4.dp)
            .clickable(onClick = onClick)
            .background(
                color = if (hasShift) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
        if (hasShift) {
            shifts.forEach { shift ->
                Text(
                    text = shift.replace(" - ", "\n"),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun ShiftDialog(
    date: LocalDate,
    hasShift: Boolean,
    shifts: List<String>,
    onDismiss: () -> Unit,
    onAddShift: () -> Unit,
    onEditShift: () -> Unit,
    onDeleteShift: () -> Unit
) {
    // Dummy data for employees
    val employees = listOf(
        "John Doe" to "09:00 - 17:00",
        "Jane Smith" to "08:00 - 16:00",
        "Emily Johnson" to "10:00 - 18:00",
        "Michael Brown" to "07:00 - 15:00",
        "Sarah Davis" to "12:00 - 20:00",
        "David Wilson" to "14:00 - 22:00",
        "Sophia Martinez" to "09:00 - 17:00",
        "Daniel Anderson" to "06:00 - 14:00",
        "Chloe Thomas" to "10:00 - 18:00",
        "Oliver Lee" to "08:00 - 16:00"
    )

    // Add user's shift to the list
    val employeesWithUser = listOf(stringResource(R.string.you) to shifts.joinToString(", ")) + employees

    // Sort the list by starting time
    val sortedEmployeesWithUser = employeesWithUser.sortedBy {
        val startTime = it.second.takeIf { it.isNotEmpty() }?.split(" - ")?.first() ?: "00:00"
        startTime.replace(":", "").toInt()
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.shift_details),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row {
                        if (hasShift) {
                            IconButton(onClick = onEditShift) {
                                Icon(
                                    painter = painterResource(id = R.drawable.edit),
                                    contentDescription = stringResource(R.string.edit_shift),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = onDeleteShift) {
                                Icon(
                                    painter = painterResource(id = R.drawable.delete),
                                    contentDescription = stringResource(R.string.delete_shift),
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(
                                painter = painterResource(id = R.drawable.close),
                                contentDescription = stringResource(R.string.close_dialog),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${stringResource(R.string.date)}: ${date.dayOfMonth} ${date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())}, ${date.year}",
                    style = MaterialTheme.typography.bodyMedium
                )
                if (hasShift) {
                    Spacer(modifier = Modifier.height(8.dp))
                    shifts.forEach { shift ->
                        Text(
                            text = "${stringResource(R.string.your_shift)}: $shift",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.other_employees_working),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        sortedEmployeesWithUser.forEach { (name, hours) ->
                            val isUser = name == stringResource(R.string.you)
                            ListItem(
                                colors = ListItemDefaults.colors(
                                    containerColor = if (isUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant
                                ),
                                headlineContent = {
                                    Text(
                                        name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                    )
                                },
                                trailingContent = {
                                    Text(
                                        hours,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = if (isUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            )
                            HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
                        }
                    }
                } else {
                    Text(
                        text = stringResource(R.string.no_shifts_scheduled),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {
            if (!hasShift) {
                Button(onClick = onAddShift) {
                    Text(stringResource(R.string.add_shift))
                }
            }
        },
        dismissButton = {}
    )
}
