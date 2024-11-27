package com.example.shiftschedules

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.shiftschedules.ui.navigation.NavGraph
import com.example.shiftschedules.ui.theme.ShiftSchedulesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShiftSchedulesTheme {
                Surface {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        hoursWorkedThisWeek = "40",
                        hoursRemainingThisWeek = "10",
                        hoursWorkedThisMonth = "160",
                        totalHoursWorked = "2000",
                        daysWorked = "250"
                    )
                }
            }
        }
    }
}
