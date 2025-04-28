package com.example.onebancrestaurant.screens

import android.widget.Toast
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.onebancrestaurant.R
import com.example.onebancrestaurant.itemView.CusineItem
import com.example.onebancrestaurant.itemView.TopDishesItem
import com.example.onebancrestaurant.navigation.Routes
import com.example.onebancrestaurant.ui.theme.OneBancRestaurantTheme
import com.example.onebancrestaurant.viewmodels.CartViewModel
import com.example.onebancrestaurant.viewmodels.GetItemListViewModel

@Composable
fun Home(navController: NavHostController, cartVM: CartViewModel) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = { TopBar(navController,"Home") }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            CardList(navController,cartVM)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, title:String) {
    val context = LocalContext.current
    TopAppBar(
        title = { Text(title, fontSize = 20.sp) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(182, 147, 82),
            titleContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = {
                Toast.makeText(context, "Language Selection feature is under development", Toast.LENGTH_SHORT).show()
            }) {
                Icon(painter = painterResource(id = R.drawable.translate), contentDescription = "translate", tint = Color.White)
            }
            IconButton(onClick = {
                navController.navigate("cart")
            }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "cart", tint = Color.White)
            }
        }
    )
}

@Composable
fun CardList(navController: NavHostController, cartVM: CartViewModel) {
    val itemListVM: GetItemListViewModel = viewModel()
    itemListVM.fetchItems()
    val itemList by itemListVM.itemList.observeAsState(null)
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp

    val lazyColumnState = rememberLazyListState()

    val lazyRowState = rememberLazyListState(
        initialFirstVisibleItemIndex = Int.MAX_VALUE / 2
    )

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
    ){
        val (infiList,topDishesText,topList,madeWithLoveText) = createRefs()

        LazyRow(
            state = lazyRowState,
            modifier = Modifier.fillMaxWidth().constrainAs(infiList){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            },
            flingBehavior = rememberSnapFlingBehavior(lazyRowState)
        ) {
            itemList?.cuisines?.let { cuisines ->
                if (cuisines.isNotEmpty()) {
                    items(
                        count = Int.MAX_VALUE,
                        key = { index -> index }
                    ) { index ->

                        val actualIndex = index % cuisines.size
                        val cuisine = cuisines[actualIndex]

                        Box(
                            modifier = Modifier
                                .width(screenWidth)
                        ) {
                            CusineItem(
                                cusineId = cuisine.cuisine_id,
                                cusineImgUrl = cuisine.cuisine_image_url,
                                cusineName = cuisine.cuisine_name,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = "Top Dishes",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
                .constrainAs(topDishesText){
                    start.linkTo(parent.start)
                    top.linkTo(infiList.bottom, margin = 5.dp)
                }
        )

        val topDishes = itemList?.cuisines
            ?.flatMap { it.items }
            ?.sortedByDescending { it.rating.toFloat() }
            ?.take(3)
            ?: emptyList()

        LazyRow(
            state = lazyColumnState, // Rename to lazyRowState if needed
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topList) {
                    top.linkTo(topDishesText.bottom, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            items(topDishes){ dish->
                val dId = dish.id
                val dUrl = dish.image_url
                val dName = dish.name
                val dPrice = dish.price
                val dRate = dish.rating


                TopDishesItem(
                    cartVM,
                    dId,
                    dUrl,
                    dName,
                    dPrice,
                    dRate
                )
            }
        }

        Text(
            text = "Made with ❤️ under 36 Hours",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier
                .constrainAs(madeWithLoveText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
//    OneBancRestaurantTheme {
//        val navController = rememberNavController()
//        Home(navController)
//    }
}