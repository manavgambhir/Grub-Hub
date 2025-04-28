package com.example.onebancrestaurant.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.onebancrestaurant.screens.*
import com.example.onebancrestaurant.viewmodels.CartViewModel

@Composable
fun NavGraph(navHostController: NavHostController) {
    val cartVM: CartViewModel = viewModel()
    NavHost(navController = navHostController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) {
            Home(navHostController,cartVM)
        }

        composable(Routes.CusineMenu.route) {
            val cusineId = it.arguments?.getString("cusineId")
            if (cusineId != null) {
                CusineMenu(navHostController,cartVM,cusineId)
            }
        }

        composable(Routes.Cart.route) {
            Cart(navHostController,cartVM)
        }

        composable(Routes.BottomNav.route) {
            BottomNav(navHostController)
        }
    }
}