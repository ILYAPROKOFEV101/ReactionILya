package com.example.reaction.ui.theme


import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reaction.LogekLogIn


@Preview(showBackground = true)
@Composable


fun LoginMenu() {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val smallestScreenWidthDp = configuration.smallestScreenWidthDp
    val isTablet = smallestScreenWidthDp >= 600
    val fontSizes = if (isTablet) 50.sp else 30.sp
    //val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp
    val rowHeight = if (screenHeightDp <= 900) {
        120.dp // Высота для телефона
    } else {
        225.dp // Высота для планшета
    }
    val clickedState = remember { mutableStateOf(false) }
    var score by remember { mutableStateOf(0) }
    var backgroundColor by remember { mutableStateOf(Color.Blue) }
    val font = if(isTablet) 35.sp else 16.sp
    val cornerRadius = if (isTablet) 20.dp else 10.dp // Задайте желаемый радиус закругления

    Column(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
        verticalArrangement = Arrangement.Center,


    )
    {
            Row(modifier = Modifier.fillMaxWidth()){


                IconButton(
                    onClick = { /* Действие при нажатии на кнопку */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Person Icon",
                        tint = Color.Blue,
                        modifier = Modifier.size(45.dp)
                    )

                }

                IconButton(
                    onClick = {
                        fun openNewActivity(context: Context) {
                            val intent = Intent(context, LogekLogIn::class.java)
                            context.startActivity(intent)
                        }
                    }// суда
                ) {
                Icon(
                    imageVector = Icons.Default.Login,
                    contentDescription = "Login Icon",
                    tint = Color.Blue,
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .size(50.dp)
                        .fillMaxHeight()
                )}
                    Text(text = "LogIn", fontSize = fontSizes, fontWeight = FontWeight.Bold,
                        modifier =  Modifier.padding(top = 3.dp))
        }

        Row(modifier = Modifier.fillMaxWidth()){


            IconButton(
                onClick = { /* Действие при нажатии на кнопку */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Person Icon",
                    tint = Color.Blue,
                    modifier = Modifier.size(45.dp)
                )

            }

            IconButton(
                onClick = { /* Действие при нажатии на кнопку */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Login Icon",
                    tint = Color.Blue,
                    modifier = Modifier
                        .padding(start = 0.dp)
                        .size(50.dp)
                        .fillMaxHeight()
                )}
            Text(text = "LogOut", fontSize = fontSizes, fontWeight = FontWeight.Bold,
                modifier =  Modifier.padding(top = 3.dp))
        }
}
}
@Preview(showBackground = true)
@Composable
fun looklile(){

}



