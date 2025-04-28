package com.example.onebancrestaurant.navigation

sealed class Routes(val route:String) {
    object Home:Routes("home")
    object BottomNav:Routes("bottomNav")
    object CusineMenu:Routes("cusineMenu/{cusineId}")
    object Cart:Routes("cart")
}