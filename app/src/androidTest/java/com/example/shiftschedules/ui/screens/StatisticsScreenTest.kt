package com.example.shiftschedules.ui.screens


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.yml.charts.common.model.Point
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StatisticsScreenComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testMetricsSectionDisplaysAllMetrics() {
        composeTestRule.setContent {
            MetricsSection()
        }

        // Verify that all metric cards are displayed
        val metrics = listOf(
            "Total Hours Worked" to "1,200 hrs",
            "Total Days Worked" to "150 days",
            "Sick Leaves Taken" to "5 Days",
            "Longest Shift" to "12 hrs, Aug 21",
            "Most Worked Day" to "Mondays",
            "Most Worked Week" to "Aug 1 - Aug 7",
            "Most Assigned Position" to "Cashier",
            "Colleague Most Worked With" to "Naeem"
        )

        metrics.forEach { (title, value) ->
            composeTestRule.onNodeWithText(title).assertIsDisplayed()
            composeTestRule.onNodeWithText(value).assertIsDisplayed()
        }
    }

    @Test
    fun testMetricCardDisplaysCorrectData() {
        composeTestRule.setContent {
            MetricCard(title = "Sample Metric", value = "Sample Value")
        }

        // Verify that the card displays the correct title and value
        composeTestRule.onNodeWithText("Sample Metric").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sample Value").assertIsDisplayed()
    }
}
