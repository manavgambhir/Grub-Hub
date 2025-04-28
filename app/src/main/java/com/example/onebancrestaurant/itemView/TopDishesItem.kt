package com.example.onebancrestaurant.itemView

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.onebancrestaurant.CartItem
import com.example.onebancrestaurant.R
import com.example.onebancrestaurant.viewmodels.CartViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TopDishesItem(cartVm:CartViewModel,cusineId:String,cusineImgUrl: String, cusineName: String, cusinePrice: String, cusineRate: String) {
    var isAdded by remember { mutableStateOf(false) }  // To track if "Add" is clicked
    val cartItems by cartVm.cartItems.observeAsState(null)
    var currentQuantity = cartItems?.get(cusineId)?.quantity ?: 0
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(270.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 🍽️ Dish Image (Top)
            GlideImage(
                model = cusineImgUrl,
                contentDescription = "Dish Image",
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop,
                failure = placeholder(R.drawable.placeholder),
                loading = placeholder(R.drawable.placeholder)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 🍛 Dish Name (Bold)
            Text(
                text = cusineName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            // 💰 Dish Price
            Text(
                text = "₹$cusinePrice",
                fontSize = 14.sp,
                color = Color.Blue,
                modifier = Modifier.padding(top = 4.dp)
            )

            // ⭐ Rating with Icon
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp, bottom = 8.dp).offset(x = -5.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFD4AF37),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = cusineRate,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            if (currentQuantity == 0) {
                // Initial "Add" Button
                TextButton(
                    onClick = {
                        cartVm.addItem(
                            CartItem(
                                id = cusineId,
                                name = cusineName,
                                price = cusinePrice.toDoubleOrNull() ?: 0.0,
                                quantity = 1,
                                imageUrl = cusineImgUrl
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .background(Color.Black, RoundedCornerShape(8.dp))
                        .width(90.dp)
                        .height(40.dp)
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
                ) {
                    IconButton(
                        onClick = { cartVm.decrementQuantity(cusineId) },
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
                        onClick = { cartVm.incrementQuantity(cusineId) },
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

@Composable
@Preview
fun TopDishesItemPreview() {
//    TopDishesItem("https://picsum.photos/id/237/200/300", "Dosa", "199", "4.9")
}
