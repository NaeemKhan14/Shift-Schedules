package com.example.shiftschedules.ui.navigation

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shiftschedules.R
import com.example.shiftschedules.data.models.SharedViewModel
import com.example.shiftschedules.domain.models.BottomNavItem
import com.example.shiftschedules.utils.NoRippleInteractionSource

@Composable
fun BottomNavBar(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    var isCameraMenuExpanded by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            sharedViewModel.setSelectedImageUri(uri) // Pass the selected image URI to the ViewModel
            navController.navigate("camera") // Navigate to the Camera screen
        }
    }
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
        if (uri != null) {
            sharedViewModel.selectedPdfUri.value = uri
            navController.navigate("pdfProcessing")
        }
    }

    val items = listOf(
        BottomNavItem(stringResource(R.string.nav_home), R.drawable.ic_home, "dashboard"),
        BottomNavItem(stringResource(R.string.nav_shifts), R.drawable.calendar_month, "shifts"),
        BottomNavItem(stringResource(R.string.nav_camera), R.drawable.ic_camera, "camera"),
        BottomNavItem(stringResource(R.string.nav_statistics), R.drawable.ic_statistics, "statistics"),
        BottomNavItem(stringResource(R.string.nav_settings), R.drawable.ic_settings, "settings")
    )

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
                            overflow = TextOverflow.Ellipsis
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
                            text = { Text(stringResource(R.string.upload_image), maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            onClick = {
                                isCameraMenuExpanded = false
                                galleryLauncher.launch("image/*")
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.upload_pdf), maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            onClick = {
                                isCameraMenuExpanded = false
                                openPDFPicker(pdfPickerLauncher)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.take_picture), maxLines = 1, overflow = TextOverflow.Ellipsis) },
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
                            overflow = TextOverflow.Ellipsis
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

fun openPDFPicker(launcher: ActivityResultLauncher<Intent>) {
    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        type = "application/pdf"
        addCategory(Intent.CATEGORY_OPENABLE)
    }
    launcher.launch(intent)
}