package com.example.shiftschedules.ui.navigation

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shiftschedules.ui.screens.DashboardScreen
import com.example.shiftschedules.ui.screens.ShiftsScreen
import com.example.shiftschedules.ui.screens.SettingsScreen
import com.example.shiftschedules.ui.screens.StatisticsScreen
import com.example.shiftschedules.R
import com.example.shiftschedules.data.models.SharedViewModel
import com.example.shiftschedules.ui.screens.CameraScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    sharedViewModel: SharedViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "dashboard",
        modifier = modifier
    ) {
        // Dashboard/Home Screen
        composable("dashboard") {
            DashboardScreen(
                bannerText = stringResource(R.string.banner_text),
                location = stringResource(R.string.location),
                days = listOf(
                    stringResource(R.string.day_tue) to "28",
                    stringResource(R.string.day_wed) to "29",
                    stringResource(R.string.day_thu) to "30",
                    stringResource(R.string.day_fri) to "1",
                    stringResource(R.string.day_sat) to "2",
                    stringResource(R.string.day_sun) to "3",
                    stringResource(R.string.day_mon) to "4",
                ),
                weeklyStats = listOf(
                    stringResource(R.string.total_shifts) to "5 Shifts",
                    stringResource(R.string.hours_worked) to "9 Hours",
                    stringResource(R.string.weekly_quota) to "+8 Hours"
                ),
                monthlyStats = listOf(
                    stringResource(R.string.total_shifts) to "5 Shifts",
                    stringResource(R.string.hours_worked) to "9 Hours",
                    stringResource(R.string.remaining_hours) to "8 Hours"
                ),
                nextShiftData = listOf(
                    stringResource(R.string.next_shift_date) to "Dec 2, Sat",
                    stringResource(R.string.next_shift_time) to "9:00 AM\n5:00 PM",
                    stringResource(R.string.total_hours) to "8 Hours"
                ),
                prevShiftData = listOf(
                    stringResource(R.string.prev_shift_date) to "Dec 2, Sat",
                    stringResource(R.string.prev_shift_time) to "9:00 AM\n5:00 PM",
                    stringResource(R.string.total_hours) to "8 Hours"
                ),
            )
        }

        // History Screen
        composable("shifts") { ShiftsScreen() }

        // Statistics Screen
        composable("statistics") { StatisticsScreen() }

        // Settings Screen
        composable("settings") { SettingsScreen() }

        // Camera Screen
        composable("camera") { CameraScreen(sharedViewModel) }
    }
}
