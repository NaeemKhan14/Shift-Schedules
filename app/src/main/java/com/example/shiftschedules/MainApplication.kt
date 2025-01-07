package com.example.shiftschedules

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.shiftschedules.data.models.SharedViewModel
import com.example.shiftschedules.ui.navigation.BottomNavBar
import com.example.shiftschedules.ui.navigation.NavigationHost
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainApplication() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()

    Scaffold(
        bottomBar = { BottomNavBar(
            navController = navController,
            sharedViewModel = sharedViewModel
        ) }
    ) { innerPadding ->
        NavigationHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            sharedViewModel = sharedViewModel
        )
    }
}