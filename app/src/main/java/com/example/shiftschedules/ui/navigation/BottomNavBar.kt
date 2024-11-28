import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.example.shiftschedules.R
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shiftschedules.domain.models.BottomNavItem
import com.example.shiftschedules.domain.models.NoRippleInteractionSource


@Composable
fun BottomNavBar(navController: NavHostController) {
    // Define the items in the bottom navigation
    val items = listOf(
        BottomNavItem("Home", R.drawable.ic_home, "dashboard"),
        BottomNavItem("History", R.drawable.ic_history, "history"),
        BottomNavItem("Camera", R.drawable.ic_camera, "camera"),
        BottomNavItem("Statistics", R.drawable.ic_statistics, "statistics"),
        BottomNavItem("Settings", R.drawable.ic_settings, "settings")
    )

    // Get the current route from the NavController
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background, // Global background color
        contentColor = MaterialTheme.colorScheme.primary // Default content color
    ) {
        items.forEach { item ->
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