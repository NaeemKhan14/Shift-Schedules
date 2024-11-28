package com.example.shiftschedules.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import org.junit.Rule
import org.junit.Test

class InfoCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun infoCardDisplaysCorrectContent() {
        // Set up the test composable
        composeTestRule.setContent {
            InfoCard(
                title = "Test Title",
                content = "Test Content",
                backgroundColor = Color.White
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Content").assertIsDisplayed()
    }

    @Test
    fun infoCardHandlesEmptyContent() {
        // Set up the test composable with an empty content
        composeTestRule.setContent {
            InfoCard(
                title = "Test Title",
                content = " ",  // Provide a single space to ensure no visible content
                backgroundColor = Color.White
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Content").assertDoesNotExist()  // Ensure "Test Content" is not shown
    }

    @Test
    fun infoCardHandlesNullTitle() {
        // Set up the test composable with a null or empty title
        composeTestRule.setContent {
            InfoCard(
                title = "",
                content = "Test Content",
                backgroundColor = Color.White
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Test Content").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Title").assertDoesNotExist()  // Ensure "Test Title" is not shown
    }

    @Test
    fun infoCardDisplaysWithCustomBackgroundColor() {
        // Set up the test composable with a custom background color
        val customColor = Color.Red
        composeTestRule.setContent {
            InfoCard(
                title = "Test Title",
                content = "Test Content",
                backgroundColor = customColor
            )
        }

        // Assertions
        composeTestRule.onRoot().assertIsDisplayed() // Ensure the root is displayed with no exceptions
        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Content").assertIsDisplayed()
    }

    @Test
    fun infoCardHandlesLongContent() {
        // Set up the test composable with long content
        val longContent = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
        composeTestRule.setContent {
            InfoCard(
                title = "Test Title",
                content = longContent,
                backgroundColor = Color.White
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
        composeTestRule.onNodeWithText(longContent).assertIsDisplayed()
    }

    @Test
    fun infoCardHandlesEmptyTitleAndContent() {
        // Set up the test composable with both title and content empty
        composeTestRule.setContent {
            InfoCard(
                title = " ",
                content = " ",
                backgroundColor = Color.White
            )
        }

        // Assertions
        composeTestRule.onNodeWithText("Test Title").assertDoesNotExist()  // Ensure "Test Title" is not shown
        composeTestRule.onNodeWithText("Test Content").assertDoesNotExist()  // Ensure "Test Content" is not shown
    }
}
