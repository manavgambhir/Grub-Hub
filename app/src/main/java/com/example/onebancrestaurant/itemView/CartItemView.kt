package com.example.onebancrestaurant.itemView

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.onebancrestaurant.CartItem
import com.example.onebancrestaurant.viewmodels.CartViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartItemView(item: CartItem, cartVM: CartViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (image, name, price, quantity,deleteIc) = createRefs()

            GlideImage(
                model = item.imageUrl,
                contentDescription = "Dish Image",
                modifier = Modifier
                    .width(80.dp)
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
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(name) {
                    start.linkTo(image.end, margin = 16.dp)
                    top.linkTo(parent.top, margin = 16.dp)
                }
            )

            Text(
                text = "₹${item.price.toInt()}",
                fontSize = 16.sp,
                color = Color.Blue,
                modifier = Modifier.constrainAs(price) {
                    start.linkTo(image.end, margin = 16.dp)
                    top.linkTo(name.bottom, margin = 8.dp)
                }
            )

            Text(
                text = "Qty: ${item.quantity}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(quantity) {
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                }
            )

            IconButton(
                onClick = { //TODO: Handle delete action
                    cartVM.removeItem(item.id)
                },
                modifier = Modifier.constrainAs(deleteIc) {
                    end.linkTo(parent.end, margin = 12.dp)
                    top.linkTo(parent.top, margin = 5.dp)
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    tint = Color.Black,
                    contentDescription = "Delete Item"
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemViewPreview() {
    val cartVM: CartViewModel = viewModel()
    CartItemView(CartItem("1", "Dish Name", 199.0, 2, "https://picsum.photos/id/237/200/300"), cartVM)
}
