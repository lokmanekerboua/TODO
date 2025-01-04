package me.lokmvne.todo

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.lokmvne.compose.ui.theme.ToDoAppTheme
import me.lokmvne.todo.presentstion.navigation.MainNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoAppTheme {
                val requestPermissions = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = {}
                )
                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.TIRAMISU) {
                    requestPermissions.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                navHostController = rememberNavController()
                MainNavGraph(navHostController)
            }
        }
    }
}
