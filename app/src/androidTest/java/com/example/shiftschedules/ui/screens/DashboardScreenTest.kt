package com.example.shiftschedules.ui.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dashboardScreenDisplaysBannerAndLocationText() {
        // Set up the test composable
        composeTestRule.setContent {
            DashboardScreen(
                bannerText = "Your Shifts",
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
                    "Date" to "2024-11-28",
                    "Time" to "9:00 AM",
                    "Hours" to "8 hours"
                ),
                prevShiftData = listOf(
                    "Date" to "2024-11-27",
                    "Time" to "9:00 AM",
                    "Hours" to "8 hours"
                )
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Your Shifts").assertIsDisplayed()
        composeTestRule.onNodeWithText("Berlin, Germany").assertIsDisplayed()
    }

    @Test
    fun dashboardScreenHandlesEmptyDaysList() {
        // Set up the test composable with an empty days list
        composeTestRule.setContent {
            DashboardScreen(
                bannerText = "Your Shifts",
                location = "Berlin, Germany",
                days = emptyList(),
                weeklyStats = emptyList(),
                monthlyStats = emptyList(),
                nextShiftData = listOf(
                    "Date" to "2024-11-28",
                    "Time" to "9:00 AM",
                    "Hours" to "8 hours"
                ),
                prevShiftData = emptyList()
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Your Shifts").assertIsDisplayed()
        composeTestRule.onNodeWithText("Berlin, Germany").assertIsDisplayed()
    }

    @Test
    fun dashboardScreenDisplaysNextShiftDetails() {
        // Set up the test composable
        composeTestRule.setContent {
            DashboardScreen(
                bannerText = "Your Shifts",
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
                weeklyStats = emptyList(),
                monthlyStats = emptyList(),
                nextShiftData = listOf(
                    "Date" to "2024-11-28",
                    "Time" to "9:00 AM",
                    "Hours" to "8 hours"
                ),
                prevShiftData = emptyList()
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("2024-11-28").assertIsDisplayed()
        composeTestRule.onNodeWithText("9:00 AM").assertIsDisplayed()
        composeTestRule.onNodeWithText("8 hours").assertIsDisplayed()
    }

    @Test
    fun dashboardScreenHandlesNoNextShift() {
        // Set up the test composable with empty next shift details
        composeTestRule.setContent {
            DashboardScreen(
                bannerText = "Your Shifts",
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
                weeklyStats = emptyList(),
                monthlyStats = emptyList(),
                nextShiftData = emptyList(),
                prevShiftData = emptyList()
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Your Shifts").assertIsDisplayed()
        composeTestRule.onNodeWithText("Berlin, Germany").assertIsDisplayed()
        composeTestRule.onNodeWithText("2024-11-28").assertDoesNotExist()  // Ensure next shift details are not shown
    }

    @Test
    fun dashboardScreenHandlesMultipleDays() {
        // Set up the test composable with multiple days
        val daysList = listOf(
            "Sun" to "26",
            "Mon" to "27",
            "Tue" to "28",
            "Wed" to "29",
            "Thu" to "30",
            "Fri" to "1",
            "Sat" to "2"
        )
        composeTestRule.setContent {
            DashboardScreen(
                bannerText = "Your Shifts",
                location = "Berlin, Germany",
                days = daysList,
                weeklyStats = emptyList(),
                monthlyStats = emptyList(),
                nextShiftData = listOf(
                    "Date" to "2024-11-28",
                    "Time" to "9:00 AM",
                    "Hours" to "8 hours"
                ),
                prevShiftData = emptyList()
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Sun").assertIsDisplayed()
        composeTestRule.onNodeWithText("26").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mon").assertIsDisplayed()
        composeTestRule.onNodeWithText("27").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tue").assertIsDisplayed()
        composeTestRule.onNodeWithText("28").assertIsDisplayed()
    }

    @Test
    fun dashboardScreenHandlesLongBannerTextAndLocation() {
        // Set up the test composable with a long banner text
        val longBannerText = "This is a long banner text that tests the layout's ability to handle large text blocks."
        composeTestRule.setContent {
            DashboardScreen(
                bannerText = longBannerText,
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
                weeklyStats = emptyList(),
                monthlyStats = emptyList(),
                nextShiftData = listOf(
                    "Date" to "2024-11-28",
                    "Time" to "9:00 AM",
                    "Hours" to "8 hours"
                ),
                prevShiftData = emptyList()
            )
        }

        // Assertions
        composeTestRule.onNodeWithText(longBannerText, substring = true, useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Berlin, Germany").assertIsDisplayed()
    }
}
