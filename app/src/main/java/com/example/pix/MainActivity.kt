package com.example.pix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.pix.domain.entity.Picture
import com.example.pix.navigation.Route
import com.example.pix.presentation.DetailedPictureViewModel
import com.example.pix.ui.details.DetailedPictureScreen
import com.example.pix.ui.grid.GridPicturesScreen
import com.example.pix.ui.theme.PixTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            PixTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Route.Grid
                    ) {
                        composable<Route.Grid> {
                            GridPicturesScreen(
                                modifier = Modifier.fillMaxSize(),
                                onClick = { picture: String ->
                                    navController.navigate(Route.Details(picture))
                                }
                            )
                        }
                        composable<Route.Details> {
                            val string = it.toRoute<Route.Details>().picture
                            val picture =
                                Json.decodeFromString<Picture>(string)
                            val viewModel =
                                hiltViewModel<DetailedPictureViewModel, DetailedPictureViewModel.DetailedPictureViewModelFactory>(
                                    creationCallback = { factory -> factory.create(picture) }
                                )
                            DetailedPictureScreen(
                                modifier = Modifier.fillMaxSize(),
                                onBack = {
                                    navController.navigateUp()
                                },
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
