package com.example.shiftschedules.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shiftschedules.ui.screens.DashboardScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    hoursWorkedThisWeek: String,
    hoursRemainingThisWeek: String,
    hoursWorkedThisMonth: String,
    totalHoursWorked: String,
    daysWorked: String
) {
    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {
        composable(route = "dashboard") {
            DashboardScreen(
                hoursWorkedThisWeek = hoursWorkedThisWeek,
                hoursRemainingThisWeek = hoursRemainingThisWeek,
                hoursWorkedThisMonth = hoursWorkedThisMonth,
                totalHoursWorked = totalHoursWorked,
                daysWorked = daysWorked
            )
        }
    }
}
