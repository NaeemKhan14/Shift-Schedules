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
                days = listOf("Monday" to "8:00 AM - 5:00 PM"),
                nextShiftDate = "2024-11-28",
                nextShiftTime = "9:00 AM",
                nextShiftHours = "8 hours"
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
                nextShiftDate = "2024-11-28",
                nextShiftTime = "9:00 AM",
                nextShiftHours = "8 hours"
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
                days = listOf("Monday" to "8:00 AM - 5:00 PM"),
                nextShiftDate = "2024-11-28",
                nextShiftTime = "9:00 AM",
                nextShiftHours = "8 hours"
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
                days = listOf("Monday" to "8:00 AM - 5:00 PM"),
                nextShiftDate = "",
                nextShiftTime = "",
                nextShiftHours = ""
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
            "Monday" to "8:00 AM - 5:00 PM",
            "Tuesday" to "9:00 AM - 6:00 PM",
            "Wednesday" to "8:00 AM - 4:00 PM"
        )
        composeTestRule.setContent {
            DashboardScreen(
                bannerText = "Your Shifts",
                location = "Berlin, Germany",
                days = daysList,
                nextShiftDate = "2024-11-28",
                nextShiftTime = "9:00 AM",
                nextShiftHours = "8 hours"
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Monday").assertIsDisplayed()
        composeTestRule.onNodeWithText("8:00 AM - 5:00 PM").assertIsDisplayed()
        composeTestRule.onNodeWithText("Tuesday").assertIsDisplayed()
        composeTestRule.onNodeWithText("9:00 AM - 6:00 PM").assertIsDisplayed()
        composeTestRule.onNodeWithText("Wednesday").assertIsDisplayed()
        composeTestRule.onNodeWithText("8:00 AM - 4:00 PM").assertIsDisplayed()
    }

    @Test
    fun dashboardScreenHandlesLongBannerTextAndLocation() {
        // Set up the test composable with a long banner text
        val longBannerText = "This is a long banner text that tests the layout's ability to handle large text blocks."
        composeTestRule.setContent {
            DashboardScreen(
                bannerText = longBannerText,
                location = "Berlin, Germany",
                days = listOf("Monday" to "8:00 AM - 5:00 PM"),
                nextShiftDate = "2024-11-28",
                nextShiftTime = "9:00 AM",
                nextShiftHours = "8 hours"
            )
        }

        // Assertions
        composeTestRule.onNodeWithText(longBannerText, substring = true, useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText("Berlin, Germany").assertIsDisplayed()
    }

}
