package pl.bartelomelo.paybackcodingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.bartelomelo.paybackcodingchallenge.screens.imagelistscreen.ImageListScreen
import pl.bartelomelo.paybackcodingchallenge.ui.theme.PaybackCodingChallengeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaybackCodingChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "image_list_screen") {
                        composable("image_list_screen") {
                            //composable function here.
                            ImageListScreen()
                        }
                        composable("image_detail_screen") {
                            //about image composable here.
                        }
                    }
                }
            }
        }
    }
}