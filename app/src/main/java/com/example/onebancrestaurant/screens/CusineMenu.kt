package com.example.onebancrestaurant.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.onebancrestaurant.data.models.MenuItem
import com.example.onebancrestaurant.itemView.DishItem
import com.example.onebancrestaurant.viewmodels.*

@Composable
fun CusineMenu(navController: NavHostController, cartVM: CartViewModel, cusineId:String){
    cartVM.cartItems.observeAsState(initial = emptyMap())
    LaunchedEffect(Unit) {
        cartVM.fetchCartItems()
    }
    val lazyListState = rememberLazyListState()
    val getItemsVM: GetItemListViewModel = viewModel()
    getItemsVM.fetchItems()
    val itemList by getItemsVM.itemList.observeAsState(null)
    val filteredCuisine = itemList?.cuisines?.find { it.cuisine_id == cusineId }
    val cuisineName = filteredCuisine?.cuisine_name ?: ""
    val dishes = filteredCuisine?.items ?: emptyList()

    Scaffold(
        topBar = { TopBar(navController,"$cuisineName Menu") }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            DishList(navController,cartVM,dishes,lazyListState)
        }
    }
}

@Composable
fun DishList(navController: NavHostController, cartVM: CartViewModel,dishes:List<MenuItem>,lazyListState:LazyListState) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (dishList, checkoutBtn) = createRefs()

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(dishList) {
                    top.linkTo(parent.top, margin = 8.dp)
                    bottom.linkTo(checkoutBtn.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
        ) {
            items(dishes) { dish ->
                val dId = dish.id
                val dUrl = dish.image_url
                val dName = dish.name
                val dPrice = dish.price

                DishItem(
                    dId,
                    dUrl,
                    dName,
                    dPrice,
                    cartVM
                )
            }
        }

        ElevatedButton(onClick = {
            navController.navigate("cart")
        }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                       modifier = Modifier.fillMaxWidth()
                           .padding(15.dp)
                           .constrainAs(checkoutBtn) {
                               start.linkTo(parent.start)
                               end.linkTo(parent.end)
                               bottom.linkTo(parent.bottom, margin = 10.dp)
                           }
        ) {
            Text(text = "Proceed to Checkout", fontSize = 16.sp, modifier = Modifier)
        }
    }
}
