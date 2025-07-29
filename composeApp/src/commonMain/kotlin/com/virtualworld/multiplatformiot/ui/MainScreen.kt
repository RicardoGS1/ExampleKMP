package com.virtualworld.multiplatformiot.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.waterfall
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

import androidx.navigation.compose.rememberNavController
import com.virtualworld.multiplatformiot.navigation.AppNavHost


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {


    val navController = rememberNavController()

    val statusSinBarValues = WindowInsets.waterfall.asPaddingValues()
    val statusBarValues = WindowInsets.safeDrawing.asPaddingValues()

    Surface(modifier = Modifier.fillMaxSize()) {

        AppNavHost(navController, statusSinBarValues)

    }

}