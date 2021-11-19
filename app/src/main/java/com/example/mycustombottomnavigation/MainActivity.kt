package com.example.mycustombottomnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycustombottomnavigation.ui.theme.MyCustomBottomNavigationTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCustomBottomNavigationTheme {
                MyApp()
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun MyApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            ExpandableBottomNavigationBar(
                menuItems = listOfMenus,
                navController = navController,
                onBottomMenuItemClicked = {
                    navController.navigate(it.route){
                        popUpTo("home")
                        launchSingleTop = true
                    }
                }
            )
        }
    ){
        SetUpNavigation(navController)
    }
}

@Composable
fun SetUpNavigation(navHost: NavHostController) {
    NavHost(navController = navHost, startDestination = "home"){
        composable("home"){
            HomeScreen()
        }
        composable("calls"){
            CallsScreen()
        }
        composable("profile"){
            ProfileScreen()
        }
        composable("settings"){
            SettingsScreen()
        }
        composable("notification"){
            NotificationsScreen()
        }
    }
}

