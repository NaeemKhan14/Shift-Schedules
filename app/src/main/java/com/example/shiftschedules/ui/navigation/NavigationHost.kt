package com.example.shiftschedules.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shiftschedules.ui.screens.DashboardScreen
import com.example.shiftschedules.ui.screens.HistoryScreen
import com.example.shiftschedules.ui.screens.SettingsScreen
import com.example.shiftschedules.ui.screens.StatisticsScreen

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "dashboard",
        modifier = modifier
    ) {
        // Dashboard/Home Screen
        composable("dashboard") {
            DashboardScreen(
                bannerText = "Starbucks",
                location = "Berlin, Germany",
                days = listOf(
                    "Sun" to "26",
                    "Mon" to "27",
                    "Tue" to "28",
                    "Wed" to "29",
                    "Thu" to "30",
                    "Fri" to "1",
                    "Sat" to "2"
                ),
                weeklyStats = listOf(
                    "Total Shifts" to "5 Shifts",
                    "Hours Worked" to "9 Hours",
                    "Weekly Quota" to "+8 Hours"
                ),
                monthlyStats = listOf(
                    "Total Shifts" to "5 Shifts",
                    "Hours Worked" to "9 Hours",
                    "Remaining Hours" to "8 Hours"
                ),
                nextShiftData = listOf(
                    "Date" to "Dec 2, Sat",
                    "Time" to "9:00 AM\n5:00 PM",
                    "Total Hours" to "8 Hours"
                ),
                prevShiftData = listOf(
                    "Date" to "Dec 2, Sat",
                    "Time" to "9:00 AM\n5:00 PM",
                    "Total Hours" to "8 Hours"
                ),
            )
        }

        // History Screen
        composable("history") { HistoryScreen() }

        // Statistics Screen
        composable("statistics") { StatisticsScreen() }

        // Settings Screen
        composable("settings") { SettingsScreen() }
        
    }
}
