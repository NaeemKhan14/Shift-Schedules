package com.example.shiftschedules.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp

@Composable
fun StatisticsScreen() {
    // Create list of points with x & y coordinates
    val pointsData: List<Point> = listOf(
        Point(0f, 0f), Point(1f, 120f), Point(2f, 150f), Point(3f, 90f),
        Point(4f, 160f), Point(5f, 130f), Point(6f, 180f),
        Point(7f, 140f), Point(8f, 170f), Point(9f, 110f),
        Point(10f, 200f), Point(11f, 150f), Point(12f, 180f)
    )

    // Set up axis data
    val xAxisData = AxisData.Builder()
        .steps(pointsData.size - 1)
        .axisLabelAngle(-50f)
        .labelData { i ->
            val months = listOf(
                "",
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec"
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

    // Set up line chart data
    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    lineStyle = LineStyle(color = Color(0xFF6200EE)),
                    intersectionPoint = IntersectionPoint(
                        color = Color(0xFFD3D3D3),
                        radius = 3.dp,
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp(
                        popUpLabel = { _, y -> y.toInt().toString() },
                        backgroundColor = Color.Transparent,
                        labelColor = Color(0xFFD3D3D3)
                    ),

                    )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = Color.Transparent
    )

    // Display the line chart
    Card (
        modifier = Modifier
            .height(300.dp)
            .padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column {
            Text(
                text = "Hours Worked This Year",
                color = Color(0xFFD3D3D3),
                modifier = Modifier.padding(10.dp)
            )
            LineChart(
                modifier = Modifier
                    .height(300.dp),
                lineChartData = lineChartData
            )
        }

    }
}
