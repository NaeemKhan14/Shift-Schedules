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
import com.example.shiftschedules.domain.models.NoRippleInteractionSource

@Composable
fun BottomNavBar(navController: NavHostController) {
    // Define the items in the bottom navigation
    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home, "dashboard"),
        BottomNavItem("Shifts", R.drawable.calendar_month, "shifts"),
        BottomNavItem("Camera", R.drawable.ic_camera, "camera"),
        BottomNavItem("Statistics", R.drawable.ic_statistics, "statistics"),
        BottomNavItem("Settings", R.drawable.ic_settings, "settings")
    )

    // Get the current route from the NavController
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    // State for managing dropdown visibility for the Camera item
    // Launcher for opening the camera to take a picture
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the captured image here
    }
    var isCameraMenuExpanded by remember { mutableStateOf(false) }

    // Launcher for picking an image from the gallery
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Handle the image URI here
    }

    // Launcher for picking a PDF file
    val pdfLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Handle the PDF URI here
    }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background, // Global background color
        contentColor = MaterialTheme.colorScheme.primary // Default content color
    ) {
        items.forEach { item ->
            if (item.label == "Camera") {
                // Special handling for Camera item to show a dropdown menu
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
                            color = if (currentRoute == item.route) Color.White else MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, // White for active tab icons
                        unselectedIconColor = MaterialTheme.colorScheme.primary, // Purple for inactive icons
                        indicatorColor = Color.Transparent // Remove overlay highlight
                    ),
                    interactionSource = NoRippleInteractionSource(),
                )

                // Dropdown menu for the Camera option
                Box (
                    modifier = Modifier.offset(x = (-100).dp)
                ) {
                    DropdownMenu(
                        expanded = isCameraMenuExpanded,
                        onDismissRequest = { isCameraMenuExpanded = false },
                    ) {
                        DropdownMenuItem(
                            text = { Text("Upload Image") },
                            onClick = {
                                isCameraMenuExpanded = false
                                // Launch gallery picker
                                isCameraMenuExpanded = false
                                galleryLauncher.launch("image/*")
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Upload PDF") },
                            onClick = {
                                isCameraMenuExpanded = false
                                // Launch file explorer for PDF
                                isCameraMenuExpanded = false
                                pdfLauncher.launch("application/pdf")
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Take Picture") },
                            onClick = {
                                isCameraMenuExpanded = false
                                // Launch camera to take a picture
                                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                cameraLauncher.launch(cameraIntent)
                            }
                        )
                    }
                }
            } else {
                // Regular NavigationBarItem for other items
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
                            color = if (currentRoute == item.route) Color.White else MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White, // White for active tab icons
                        unselectedIconColor = MaterialTheme.colorScheme.primary, // Purple for inactive icons
                        indicatorColor = Color.Transparent // Remove overlay highlight
                    ),
                    interactionSource = NoRippleInteractionSource()
                )
            }
        }
    }
}
