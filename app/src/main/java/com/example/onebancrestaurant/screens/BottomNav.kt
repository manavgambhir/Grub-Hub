package com.example.onebancrestaurant.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.onebancrestaurant.data.models.BottomNavItem
import com.example.onebancrestaurant.navigation.Routes

@Composable
fun BottomNav(navController: NavHostController) {
    val navController1 = rememberNavController()
    Scaffold(bottomBar = {
        MyBottomBar(navController1)
    }) { innerPadding->
        NavHost(
            navController = navController1,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
//            composable(Routes.Home.route){
////                Home(navController)
//            }

//            composable(Routes.Profile.routes){
//                Profile(navController)
//            }

        }
    }
}

@Composable
fun MyBottomBar(navController1: NavController){

    val backStackEntry = navController1.currentBackStackEntryAsState()
    val list = listOf(
        BottomNavItem(
            Icons.Rounded.Home,
            Routes.Home.route,
            "Home"
        )

    )

    BottomAppBar {
        list.forEach{
            val selected = it.route == backStackEntry.value?.destination?.route
            NavigationBarItem(selected = selected, onClick = {
                navController1.navigate(it.route){
                    popUpTo(navController1.graph.findStartDestination().id){
                        saveState = true
                    }
                    launchSingleTop = true
                }
            }, icon = { Icon(imageVector = it.icon, contentDescription = it.title) })
        }
    }
}