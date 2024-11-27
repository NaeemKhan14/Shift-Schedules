package com.example.shiftschedules.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shiftschedules.ui.components.StatsCard
import com.example.shiftschedules.ui.theme.CardPrimary
import com.example.shiftschedules.ui.theme.CardSecondary
import com.example.shiftschedules.ui.theme.CardTertiary

@Composable
fun DashboardScreen(
    hoursWorkedThisWeek: String,
    hoursRemainingThisWeek: String,
    hoursWorkedThisMonth: String,
    totalHoursWorked: String,
    daysWorked: String
) {
    // Column to arrange cards vertically
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Card for Hours Worked This Week
        StatsCard(
            title = "Hours Worked This Week",
            value = hoursWorkedThisWeek,
            description = "You have worked $hoursWorkedThisWeek this week.",
            backgroundColor = CardPrimary // Using theme color for primary card
        )
        Spacer(modifier = Modifier.height(16.dp)) // Space between cards

        // Card for Hours Remaining This Week
        StatsCard(
            title = "Hours Remaining This Week",
            value = hoursRemainingThisWeek,
            description = "You have $hoursRemainingThisWeek hours remaining this week.",
            backgroundColor = CardSecondary // Using theme color for secondary card
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Card for Hours Worked This Month
        StatsCard(
            title = "Hours Worked This Month",
            value = hoursWorkedThisMonth,
            description = "You have worked $hoursWorkedThisMonth this month.",
            backgroundColor = CardTertiary // Using theme color for tertiary card
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Card for Total Lifetime Hours
        StatsCard(
            title = "Total Lifetime Hours",
            value = totalHoursWorked,
            description = "Your total worked hours so far.",
            backgroundColor = CardPrimary // Using theme color for primary card
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Card for Total Days Worked
        StatsCard(
            title = "Total Days Worked",
            value = daysWorked,
            description = "You have worked $daysWorked days so far.",
            backgroundColor = CardSecondary // Using theme color for secondary card
        )
    }
}
