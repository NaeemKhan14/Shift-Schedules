package com.example.shiftschedules.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shiftschedules.ui.components.InfoCard
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.shiftschedules.R

@Composable
fun DashboardScreen(
    bannerText: String,
    location: String,
    days: List<Pair<String, String>>,
    nextShiftDate: String,
    nextShiftTime: String,
    nextShiftHours: String
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BannerSection(
                bannerText = "Your Shifts",
                location = "Berlin, Germany",
                imagePainter = painterResource(id = R.drawable.banner) // Replace with your image file name
            )
            DaysOfWeekSection(days = days)
            NextShiftSection(
                date = nextShiftDate,
                time = nextShiftTime,
                hours = nextShiftHours,
                cardColors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Composable
fun BannerSection(
    bannerText: String,
    location: String,
    imagePainter: Painter // Pass the image painter as an argument
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
    ) {
        // Background Image
        Image(
            painter = imagePainter,
            contentDescription = null, // Decorative image, no need for accessibility
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)), // Rounded corners for the image
            contentScale = ContentScale.Crop // Ensure the image scales properly
        )

        // Text Overlay
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = bannerText,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = location,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}
@Composable
fun DaysOfWeekSection(days: List<Pair<String, String>>) {
    Column {
        Text(
            text = "This Week",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            days.forEach { (day, date) ->
                DayCard(
                    day = day,
                    date = date,
                    modifier = Modifier.weight(1f) // Ensures equal width for all cards
                )
            }
        }
    }
}


@Composable
fun DayCard(day: String, date: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = date,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
fun NextShiftSection(
    date: String,
    time: String,
    hours: String,
    cardColors: List<Color>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Next Shift",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InfoCard(
                title = "Date",
                content = date,
                backgroundColor = cardColors[0],
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                title = "Time",
                content = time,
                backgroundColor = cardColors[1],
                modifier = Modifier.weight(1f)
            )
            InfoCard(
                title = "Hours",
                content = hours,
                backgroundColor = cardColors[2],
                modifier = Modifier.weight(1f)
            )
        }
    }
}