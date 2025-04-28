package com.example.onebancrestaurant.itemView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.onebancrestaurant.R // Import your R file
import com.example.onebancrestaurant.navigation.Routes

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CusineItem(cusineId: String, cusineImgUrl: String, cusineName: String, navController: NavHostController) {
    Card(
        modifier = Modifier
            .height(260.dp)
            .padding(8.dp)
            .clickable {
                val routes = Routes.CusineMenu.route.replace("{cusineId}", cusineId)
                navController.navigate(routes)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                model = cusineImgUrl,
                contentDescription = "Cuisine Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.FillBounds,
                failure = placeholder(R.drawable.placeholder),
                loading = placeholder(R.drawable.placeholder),
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(Color.Black.copy(alpha = 0.5f)) // Semi-transparent background
                    .fillMaxWidth()
                    .padding(8.dp) // Padding around the text
            ) {
                Text(
                    text = cusineName,
                    color = Color.White,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CusineItemPreview() {
//    CusineItem("https://picsum.photos/id/237/200/300","Dog")
}