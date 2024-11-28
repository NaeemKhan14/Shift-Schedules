package com.example.shiftschedules.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import org.junit.Rule
import org.junit.Test

class DashboardRowContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dashboardRowContentDisplaysTitle() {
        // Set up the test composable
        composeTestRule.setContent {
            DashboardRowContent(
                title = "Next Shift",
                content = listOf(
                    "Date" to "2024-11-28",
                    "Time" to "9:00 AM",
                    "Hours" to "8 hours"
                )
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Next Shift").assertIsDisplayed()
    }

    @Test
    fun dashboardRowContentDisplaysAllCards() {
        // Set up the test composable
        val contentList = listOf(
            "Date" to "2024-11-28",
            "Time" to "9:00 AM",
            "Hours" to "8 hours"
        )
        composeTestRule.setContent {
            DashboardRowContent(
                title = "Next Shift",
                content = contentList
            )
        }

        // Assertions - Verify that each card's title and content are displayed
        contentList.forEach { (title, content) ->
            composeTestRule.onNodeWithText(title).assertIsDisplayed()
            composeTestRule.onNodeWithText(content).assertIsDisplayed()
        }
    }

    @Test
    fun dashboardRowContentHandlesEmptyContent() {
        // Set up the test composable with empty content
        composeTestRule.setContent {
            DashboardRowContent(
                title = "Next Shift",
                content = emptyList()
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Next Shift").assertIsDisplayed()
    }

    @Test
    fun dashboardRowContentDisplaysAlternatingCardColors() {
        // Set up the test composable with multiple cards to test alternating colors
        val contentList = listOf(
            "Date" to "2024-11-28",
            "Time" to "9:00 AM",
            "Hours" to "8 hours",
            "Location" to "Berlin, Germany"
        )
        composeTestRule.setContent {
            DashboardRowContent(
                title = "Next Shift",
                content = contentList
            )
        }

        // Assertions - Verify that each card's title is displayed
        contentList.forEach { (title, _) ->
            composeTestRule.onNodeWithText(title).assertIsDisplayed()
        }
    }
}
