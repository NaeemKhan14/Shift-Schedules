package com.example.shiftschedules.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.*
import com.example.shiftschedules.utils.ScrollableScreen

@Composable
fun StatisticsScreen() {
    ScrollableScreen {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GraphSection(
                listOf(
                    Point(0f, 0f), Point(1f, 120f), Point(2f, 150f), Point(3f, 90f),
                    Point(4f, 160f), Point(5f, 130f), Point(6f, 180f),
                    Point(7f, 140f), Point(8f, 170f), Point(9f, 110f),
                    Point(10f, 200f), Point(11f, 150f), Point(12f, 180f)
                )
            )

            // Key Metrics Section
            Text(text = "Key Metrics", style = MaterialTheme.typography.titleMedium)
            MetricsSection()
        }
    }
}

@Composable
fun GraphSection(data: List<Point>) {
    val xAxisData = AxisData.Builder()
        .steps(data.size - 1)
        .axisLabelAngle(-50f)
        .labelData { i ->
            val months = listOf(
                "", "Jan", "Feb", "Mar", "Apr", "May",
                "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
            )
            months[i % months.size]
        }
        .axisLabelColor(Color(0xFFD3D3D3))
        .build()

    val yAxisData = AxisData.Builder()
        .steps(5)
        .labelData { i -> (i * 50).toString() }
        .backgroundColor(Color.Transparent)
        .axisLabelColor(Color(0xFFD3D3D3))
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = data,
                    lineStyle = LineStyle(color = Color(0xFF6200EE)),
                    intersectionPoint = IntersectionPoint(
                        color = Color(0xFFD3D3D3),
                        radius = 3.dp,
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp(
                        popUpLabel = { _, y -> y.toInt().toString() },
                        backgroundColor = Color.Transparent,
                        labelColor = Color(0xFFD3D3D3)
                    )
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = Color.Transparent
    )

    Card(
        modifier = Modifier
            .height(300.dp), // Fixed height retained for visual consistency
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column {
            Text(
                text = "Hours Worked This Year",
                color = Color(0xFFD3D3D3),
                modifier = Modifier.padding(10.dp),
                maxLines = 1, // Prevents overflow
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            LineChart(
                modifier = Modifier
                    .height(300.dp), // Fixed height retained for consistent design
                lineChartData = lineChartData
            )
        }
    }
}

@Composable
fun MetricsSection() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        MetricCard(title = "Total Hours Worked", value = "1,200 hrs")
        MetricCard(title = "Total Days Worked", value = "150 days")
        MetricCard(title = "Sick Leaves Taken", value = "5 Days")
        MetricCard(title = "Longest Shift", value = "12 hrs, Aug 21")
        MetricCard(title = "Most Worked Day", value = "Mondays")
        MetricCard(title = "Most Worked Week", value = "Aug 1 - Aug 7")
        MetricCard(title = "Most Assigned Position", value = "Cashier")
        MetricCard(title = "Colleague Most Worked With", value = "Naeem")
    }
}

@Composable
fun MetricCard(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1, // Prevent overflow for long titles
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1, // Prevent overflow for long values
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}
