package com.example.shiftschedules.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class BottomNavBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomNavBar_CameraMenuExpansion() {
        composeTestRule.setContent {
            TestBottomNavBar()
        }

        // Expand camera dropdown menu
        composeTestRule.onNodeWithContentDescription("Camera", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Upload Image", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Upload PDF", useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Take Picture", useUnmergedTree = true).assertExists()
    }

    @Test
    fun testBottomNavBar_GalleryOptionClicked() {
        composeTestRule.setContent {
            TestBottomNavBar()
        }

        // Expand camera dropdown menu and click "Upload Image"
        composeTestRule.onNodeWithContentDescription("Camera", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Upload Image", useUnmergedTree = true).performClick()
        // Verify that "Upload Image" action is triggered
        // Add a placeholder assertion since actual image picking is not testable here
        composeTestRule.onNodeWithText("Upload Image", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun testBottomNavBar_PdfOptionClicked() {
        composeTestRule.setContent {
            TestBottomNavBar()
        }

        // Expand camera dropdown menu and click "Upload PDF"
        composeTestRule.onNodeWithContentDescription("Camera", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Upload PDF", useUnmergedTree = true).performClick()
        // Verify that "Upload PDF" action is triggered
        // Add a placeholder assertion since actual PDF picking is not testable here
        composeTestRule.onNodeWithText("Upload PDF", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun testBottomNavBar_TakePictureOptionClicked() {
        composeTestRule.setContent {
            TestBottomNavBar()
        }

        // Expand camera dropdown menu and click "Take Picture"
        composeTestRule.onNodeWithContentDescription("Camera", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Take Picture", useUnmergedTree = true).performClick()
        // Verify that "Take Picture" action is triggered
        // Add a placeholder assertion since actual camera action is not testable here
        composeTestRule.onNodeWithText("Take Picture", useUnmergedTree = true).assertDoesNotExist()
    }

    @Composable
    private fun TestBottomNavBar() {
        val navController = rememberNavController()
        BottomNavBar(navController = navController)
    }
}
