/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.peterd.fivek.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.peterd.fivek.presentation.theme.FiveKTheme
import com.peterd.fivek.presentation.views.RunSelectorScreen
import androidx.wear.tooling.preview.devices.WearDevices.SMALL_ROUND
import com.peterd.fivek.presentation.MainActivity.Companion.workoutsData
import com.peterd.fivek.presentation.data.WorkoutData
import com.peterd.fivek.presentation.data.loadWorkoutDataFromResources
import com.peterd.fivek.presentation.views.RunScreen

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var workoutsData: List<WorkoutData>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        workoutsData = loadWorkoutDataFromResources(this)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {
            WearApp(workoutsData)
        }
    }
}

@Composable
fun WearApp(workoutsData: List<WorkoutData>) {
    val navController = rememberSwipeDismissableNavController()
    FiveKTheme {
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = "run_selector"
        ) {

            composable("run_selector") {
                RunSelectorScreen(navController = navController)
            }
            composable("week_{weekIndex}_run_{runIndex}") { backStackEntry ->
                val weekIndex = backStackEntry.arguments?.getString("weekIndex")
                val runIndex = backStackEntry.arguments?.getString("runIndex")
                RunScreen(
                    weekIndex = weekIndex!!.toInt(),
                    runIndex = runIndex!!.toInt(),
                    workoutsData = workoutsData
                )
            }
        }
    }
}

@Preview(device = SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp(workoutsData = workoutsData)
}