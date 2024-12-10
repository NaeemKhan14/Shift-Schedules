package com.example.shiftschedules.ui.navigation

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shiftschedules.R
import com.example.shiftschedules.domain.models.BottomNavItem
import com.example.shiftschedules.utils.NoRippleInteractionSource

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home, "dashboard"),
        BottomNavItem("Shifts", R.drawable.calendar_month, "shifts"),
        BottomNavItem("Camera", R.drawable.ic_camera, "camera"),
        BottomNavItem("Statistics", R.drawable.ic_statistics, "statistics"),
        BottomNavItem("Settings", R.drawable.ic_settings, "settings")
    )

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    var isCameraMenuExpanded by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { }
    val pdfLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEach { item ->
            if (item.label == "Camera") {
                NavigationBarItem(
                    selected = false,
                    onClick = { isCameraMenuExpanded = true },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = item.icon),
                            contentDescription = item.label
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            color = if (currentRoute == item.route) Color.White else MaterialTheme.colorScheme.primary,
                            maxLines = 1, // Prevent excessive height due to long text
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent
                    ),
                    interactionSource = NoRippleInteractionSource(),
                )

                Box(modifier = Modifier.offset(x = (-100).dp)) {
                    DropdownMenu(
                        expanded = isCameraMenuExpanded,
                        onDismissRequest = { isCameraMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Upload Image", maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis) },
                            onClick = {
                                isCameraMenuExpanded = false
                                galleryLauncher.launch("image/*")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Upload PDF", maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis) },
                            onClick = {
                                isCameraMenuExpanded = false
                                pdfLauncher.launch("application/pdf")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Take Picture", maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis) },
                            onClick = {
                                isCameraMenuExpanded = false
                                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                cameraLauncher.launch(cameraIntent)
                            }
                        )
                    }
                }
            } else {
                NavigationBarItem(
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = item.icon),
                            contentDescription = item.label
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            color = if (currentRoute == item.route) Color.White else MaterialTheme.colorScheme.primary,
                            maxLines = 1, // Prevent excessive height due to long text
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = Color.Transparent
                    ),
                    interactionSource = NoRippleInteractionSource()
                )
            }
        }
    }
}
