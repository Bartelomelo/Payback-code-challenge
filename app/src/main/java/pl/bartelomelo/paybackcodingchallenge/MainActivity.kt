package pl.bartelomelo.paybackcodingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import pl.bartelomelo.paybackcodingchallenge.feature_image.presentation.imagedetailscreen.ImageDetailScreen
import pl.bartelomelo.paybackcodingchallenge.feature_image.presentation.imagelistscreen.ImageListScreen
import pl.bartelomelo.paybackcodingchallenge.ui.theme.PaybackCodingChallengeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaybackCodingChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "image_list_screen") {
                        composable("image_list_screen") {
                            //composable function here.
                            ImageListScreen(navController = navController)
                        }
                        composable(
                            "image_detail_screen/{id}",
                            arguments = listOf(
                                navArgument("id") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val id = it.arguments?.getString("id")!!
                            ImageDetailScreen(navController = navController, imageId = id.toInt())
                        }
                    }
                }
            }
        }
    }
}