package com.example.onebancrestaurant.itemView

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.onebancrestaurant.CartItem
import com.example.onebancrestaurant.R
import com.example.onebancrestaurant.viewmodels.CartViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DishItem(dishId: String, dishImgUrl: String, dishName: String, dishPrice: String, cartVM: CartViewModel) {
    val cartItems by cartVM.cartItems.observeAsState(emptyMap())
    val currentQuantity = cartItems[dishId]?.quantity ?: 0
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (image, name, price, addBtn,qtyText) = createRefs()
            
            GlideImage(
                model = dishImgUrl,
                contentDescription = "Dish Image",
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                contentScale = ContentScale.Crop
            )
            
            Text(
                text = dishName,
                fontSize = 16.sp,  // Updated to match CartItemView
                fontWeight = FontWeight.Bold,  // Added bold font weight
                modifier = Modifier.constrainAs(name) {
                    start.linkTo(image.end, margin = 16.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                }
            )
            
            Text(
                text = "₹$dishPrice",
                fontSize = 16.sp,  // Updated to match CartItemView
                color = Color.Blue,
                modifier = Modifier.constrainAs(price) {
                    start.linkTo(image.end, margin = 16.dp)
                    top.linkTo(name.bottom, margin = 8.dp)
                }
            )

            if (currentQuantity == 0) {
                TextButton(
                    onClick = {
                        cartVM.addItem(
                            CartItem(
                                id = dishId,
                                name = dishName,
                                price = dishPrice.toDoubleOrNull() ?: 0.0,
                                quantity = 1,
                                imageUrl = dishImgUrl
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .background(Color.Black, RoundedCornerShape(8.dp))
                        .width(90.dp)
                        .height(40.dp)
                        .constrainAs(addBtn){
                            end.linkTo(parent.end, margin = 3.dp)
                            bottom.linkTo(parent.bottom, margin = 10.dp)
                        }
                ) {
                    Text(text = "Add", color = Color.White)
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 18.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp)
                        .height(40.dp)
                        .constrainAs(qtyText){
                            end.linkTo(parent.end, margin = 10.dp)
                            bottom.linkTo(parent.bottom, margin = -5.dp)
                        }
                ) {
                    IconButton(
                        onClick = { cartVM.decrementQuantity(dishId) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.remove),
                            contentDescription = "Decrease Quantity",
                            modifier = Modifier.size(22.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
                        )
                    }

                    Text(
                        text = currentQuantity.toString(),
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    IconButton(
                        onClick = { cartVM.incrementQuantity(dishId) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.add),
                            contentDescription = "Increase Quantity",
                            modifier = Modifier.size(22.dp),
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.Black)
                        )
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DishItemPreview() {
    val cartVM:CartViewModel = viewModel()
    DishItem("123", "https://picsum.photos/id/237/200/300","Dosa","100",cartVM)
}