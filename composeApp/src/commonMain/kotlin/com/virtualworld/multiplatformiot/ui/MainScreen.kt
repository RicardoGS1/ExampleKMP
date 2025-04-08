package com.virtualworld.multiplatformiot.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.virtualworld.multiplatformiot.navigation.AppNavHost


@Composable
fun MainScreen() {



    val navController = rememberNavController()

    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold()
        { paddingValues ->



            AppNavHost(navController, paddingValues)
        }
    }

}