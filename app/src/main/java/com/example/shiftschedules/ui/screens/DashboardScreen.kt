package com.example.shiftschedules.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shiftschedules.R
import com.example.shiftschedules.ui.components.DashboardRowContent
import com.example.shiftschedules.utils.ScrollableScreen

@Composable
fun DashboardScreen(
    bannerText: String,
    location: String,
    days: List<Pair<String, String>>,
    weeklyStats: List<Pair<String, String>>,
    monthlyStats: List<Pair<String, String>>,
    nextShiftData: List<Pair<String, String>>,
    prevShiftData: List<Pair<String, String>>,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        ScrollableScreen {
            BannerSection(
                bannerText = bannerText,
                location = location,
                imagePainter = painterResource(id = R.drawable.banner)
            )
            DaysOfWeekSection(days = days)
            DashboardRowContent(
                title = stringResource(R.string.this_week_stats),
                content = weeklyStats
            )
            DashboardRowContent(
                title = stringResource(R.string.this_month_stats),
                content = monthlyStats
            )
            DashboardRowContent(
                title = stringResource(R.string.next_shift),
                content = nextShiftData
            )
            DashboardRowContent(
                title = stringResource(R.string.previous_shift),
                content = prevShiftData
            )
        }
    }
}

@Composable
fun BannerSection(
    bannerText: String,
    location: String,
    imagePainter: Painter
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = bannerText,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Text(
                text = location,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DaysOfWeekSection(days: List<Pair<String, String>>) {
    Column {
        Text(
            text = stringResource(R.string.this_week),
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
                    modifier = Modifier.weight(1f),
                    hasShift = false
                )
            }
        }
    }
}

@Composable
fun DayCard(day: String, date: String, modifier: Modifier = Modifier, hasShift: Boolean) {
    val backgroundColor = if (hasShift) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface

    Column(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
        Text(
            text = date,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
    }
}

