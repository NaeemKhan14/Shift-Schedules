package com.example.shiftschedules.ui.screens
//
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.shiftschedules.R
//import com.example.shiftschedules.data.models.SettingsViewModel
//import org.junit.Rule
//import org.junit.Test
//
//class SettingsScreenTest {
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun testSettingsScreen_displaysAllSettingsRows() {
//        composeTestRule.setContent {
//            val viewModel: SettingsViewModel = viewModel()
//            SettingsScreen(viewModel)
//        }
//
//        composeTestRule.onNodeWithText("Name").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Hours Per Week").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Store Location").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Language").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Alarm").assertIsDisplayed()
//    }
//
//    @Test
//    fun testEditableFieldWithEditIcon_becomesEditableOnClick() {
//        composeTestRule.setContent {
//            EditableFieldWithEditIcon(hint = "Enter your name")
//        }
//
//        composeTestRule.onNodeWithContentDescription("Edit").performClick()
//        composeTestRule.onNodeWithText("Enter your name").assertExists()
//        composeTestRule.onNodeWithText("Enter your name").assertHasClickAction()
//    }
//
//    @Test
//    fun testTooltip_appearsWhenInfoIconIsClicked() {
//        composeTestRule.setContent {
//            SettingsRowWithLabel(
//                label = "Name",
//                tooltip = "Your name on shift schedule",
//                icon = R.drawable.id_card
//            ) {
//                EditableFieldWithEditIcon(hint = "Enter your name")
//            }
//        }
//
//        composeTestRule.onNodeWithContentDescription("Tooltip").performClick()
//        composeTestRule.onNodeWithText("Your name on shift schedule").assertIsDisplayed()
//    }
//
//    @Test
//    fun testLanguageDropdown_showsOptionsOnClick() {
//        composeTestRule.setContent {
//            LanguageDropdown()
//        }
//
//        composeTestRule.onNodeWithText("English").performClick()
//        composeTestRule.onNodeWithText("German").assertIsDisplayed()
//    }
//
//    @Test
//    fun testAlarmSwitchField_enablesOffsetWhenToggledOn() {
//        composeTestRule.setContent {
//            AlarmSwitchField()
//        }
//
//        // Ensure the "Alarm" toggle is displayed
//        composeTestRule.onNodeWithText("Alarm").assertIsDisplayed()
//
//        // Toggle the Alarm switch to ON
//        composeTestRule.onNode(isToggleable()).performClick()
//
//        // Verify that the "Enter offset" field is displayed and editable
//        composeTestRule.onNodeWithText("Enter offset").assertExists()
//    }
//
//
//    @Test
//    fun testAlarmOffsetField_remainsDisabledWhenAlarmIsOff() {
//        composeTestRule.setContent {
//            AlarmSwitchField()
//        }
//
//        composeTestRule.onNodeWithText("Alarm").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Enter offset").assertDoesNotExist()
//    }
//
//    @Test
//    fun testHoursPerWeekField_initialValueIsZero() {
//        composeTestRule.setContent {
//            SettingsRowWithLabel(
//                label = "Hours Per Week",
//                tooltip = "Total allowed working hours per week",
//                icon = android.R.drawable.ic_dialog_info
//            ) {
//                EditableFieldWithEditIcon(hint = "0", isIntOnly = true)
//            }
//        }
//
//        // Verify the initial value is "0"
//        composeTestRule.onNodeWithText("0").assertIsDisplayed()
//    }
//
//
//}