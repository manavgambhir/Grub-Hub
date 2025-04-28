package com.example.onebancrestaurant.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.onebancrestaurant.CartItem
import com.example.onebancrestaurant.R
import com.example.onebancrestaurant.itemView.CartItemView
import com.example.onebancrestaurant.viewmodels.CartViewModel
import com.example.onebancrestaurant.viewmodels.MakePaymentVM

@Composable
fun Cart(navController: NavHostController, cartVM: CartViewModel) {
    val cartItems by cartVM.cartItems.observeAsState(initial = emptyMap())
    val makePaymentVM: MakePaymentVM = viewModel()
    LaunchedEffect(Unit) {
        cartVM.fetchCartItems()
    }

    Scaffold(
        topBar = { CartTopBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (cartItems.isEmpty()) {
                EmptyCartMessage()
            } else {
                CartList(navController, cartItems, cartVM,makePaymentVM)
            }
        }
    }
}

@Composable
fun EmptyCartMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.empty_cart),
            contentDescription = "Empty Cart",
            modifier = Modifier.size(360.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your cart is Empty",
            textAlign = TextAlign.Center,
            fontSize = 20.sp, // Larger font size
            color = Color.Black, // Black color
            fontWeight = FontWeight.Bold // make it bold
        )
        Text(
            text = "Please add something from the menu",
            textAlign = TextAlign.Center,
            fontSize = 14.sp, // Smaller font size
            color = Color.Gray // Gray color
        )
    }
}

@Composable
fun CartList(navController: NavHostController, cartItems: Map<String, CartItem>, cartVM: CartViewModel, makePaymentVM: MakePaymentVM) {
    val totalAmount = cartItems.values.sumOf { it.price * it.quantity }
    val gstAmount = (totalAmount * 0.05).let { String.format("%.2f", it).toFloat() }
    val finalAmount = (totalAmount + gstAmount).let { String.format("%.2f", it).toFloat() }

    val paymentResponse by makePaymentVM.paymentResponse.observeAsState()

    Log.d("PaymentTest", makePaymentVM.makePayment(cartItems).toString())

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val lazyListState = rememberLazyListState()
        val (cartList, priceDetails, checkoutBtn) = createRefs()

        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(cartList) {
                    top.linkTo(parent.top, margin = 8.dp)
                    bottom.linkTo(priceDetails.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }
        ) {
            items(cartItems.values.toList()) { item ->
                Log.d("CartItem", "Item: $item")
                CartItemView(item, cartVM)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .constrainAs(priceDetails) {
                    bottom.linkTo(checkoutBtn.top, margin = 8.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material3.Text(text = "Total", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                androidx.compose.material3.Text(text = "₹${totalAmount.toInt()}", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material3.Text(text = "(CGST + SGST) 5%", fontSize = 16.sp, color = Color.Gray)
                androidx.compose.material3.Text(text = "₹${gstAmount}", fontSize = 16.sp)
            }
            Divider(modifier = Modifier.padding(vertical = 4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                androidx.compose.material3.Text(text = "Final Amount", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                androidx.compose.material3.Text(text = "₹${finalAmount}", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        var showDialog by remember { mutableStateOf(false) }

        if (showDialog) {
            OrderPlacedDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                onNavigateHome = {
                    showDialog = false
                    navController.navigate("home")
                }
            )
        }

        ElevatedButton(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .constrainAs(checkoutBtn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
        ) {
            androidx.compose.material3.Text(text = "Order Now", fontSize = 16.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar(navController: NavHostController) {
    TopAppBar(
        title = { androidx.compose.material3.Text("Your Cart", fontSize = 20.sp) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(182, 147, 82),
            titleContentColor = Color.White
        ),
        actions = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.Outlined.Close, contentDescription = "cart", tint = Color.White)
            }
        }
    )
}

@Composable
fun OrderPlacedDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onNavigateHome: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.success),
                        contentDescription = "Success",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Order Placed Successfully",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss()
                        onNavigateHome()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(text = "Go to Home", color = Color.White)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartPreview() {
    val navController = rememberNavController()
    val cartVM: CartViewModel = viewModel()
    Cart(navController, cartVM)
}