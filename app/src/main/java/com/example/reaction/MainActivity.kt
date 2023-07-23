package com.example.reaction


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.reaction.logik.USERRESULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        //    WaterSplashAnimation()
          //  FirsPage.createComposable()
           // FirsPage(openSecondActivity = ::openSecondActivity)
            createComposable()

        }

                }

   fun FirsPage(openSecondActivity: () -> Unit) {

    }

    fun openSecondActivity() {
        val intent = Intent(this@MainActivity, SecondActiviti::class.java)
        startActivity(intent)
    }
    @Composable
    fun createComposable() {
        val textResId = R.string.main_text
        val text = stringResource(id = textResId)
        val buttonColors by remember { mutableStateOf(Color.Green) }
        var clicked by remember { mutableStateOf(false) }
        var startTime by remember { mutableStateOf(0L) }
        var seconds by remember { mutableStateOf(0L) }
        var milliseconds by remember { mutableStateOf(0L) }

        var suMil by remember {
            mutableStateOf(0L)
        }
        var sumS by remember {
            mutableStateOf(0L)
        }


        var buttonColor by remember { mutableStateOf(Color.Green) }

        var buttonred by remember {
            mutableStateOf(false)
        }
        var max by remember {
            mutableStateOf(0L)
        }
        var maxsec by remember {
            mutableStateOf(0L)
        }
        var minValue by remember {
            mutableStateOf(Long.MAX_VALUE)
        }


        var minSec by remember {
            mutableStateOf(0L)
        }
        var minMil by remember {
            mutableStateOf(0L)
        }

        val intent = Intent(this, Main_menu::class.java)
        intent.putExtra("minSec", minSec)
        intent.putExtra("minMil", minMil)


        Box(modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {


            Button(
                colors = ButtonDefaults.buttonColors(buttonColor),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier.fillMaxSize(1f),
                onClick = {
                    clicked = !clicked



                    if (clicked) {
                        CoroutineScope(Dispatchers.Main).launch {
                            //delay((3..5).random() * 1000L)
                            buttonColor = Color.Red
                            buttonred = !buttonred
                        }
                    } else if (!clicked) {
                        CoroutineScope(Dispatchers.Main).launch {
                            // delay((0..0).random() * 1000L)
                            buttonColor = Color.Green
                        }
                    }
                    if (clicked) {
                        startTime = System.currentTimeMillis()
                    } else {
                        if (seconds < minValue) {
                            minValue = seconds
                            minSec = seconds
                            minMil = milliseconds
                        } else if (seconds == minValue && milliseconds < minMil) {
                            minMil = milliseconds
                        }
                    }
                },
            ) {







                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = text,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
            }

            if (clicked) {

                LaunchedEffect(Unit) {
                    while (clicked) {
                        val currentTime = System.currentTimeMillis()
                        val elapsedTime = currentTime - startTime
                        seconds = elapsedTime / 1000
                        milliseconds = elapsedTime % 1000
                        delay(10)
                        if (seconds < max) {
                            max = seconds
                        }

                        if (milliseconds < maxsec) {
                            maxsec = milliseconds
                        }
                    }
                }
            }



            if (clicked) {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 50.dp)
                ) {
                    Text(
                        text = if (clicked) "$seconds: $milliseconds" else "$sumS:$suMil",
                        color = Color.White,
                        fontSize = 30.sp,

                        //textAlign = TextAlign.C
                    )

                }
                suMil = milliseconds
                sumS = seconds


            } else {
                Column(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 50.dp)
                ) {
                    Text(
                        text = "${stringResource(id = R.string.result)}: $sumS:$suMil",
                        color = Color.White,
                        fontSize = 30.sp,

                        //textAlign = TextAlign.C
                    )
                    Text(
                        text = "${stringResource(id = R.string.bestresult)}: $minSec:$minMil",
                        color = Color.White,
                        fontSize = 30.sp,
                    )

                }


            }
            class MyViewModel : ViewModel() {
                var mili: Int = minMil.toInt()
                var secends: Int = minSec.toInt()
            }

            val originalUserData = USERRESULT(
                result = (1000 - minSec + minMil).toInt()
            )


            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                // Ваш код для остальной части экрана

                menubootom() // Вызов вашего bottombar() функции

                // Дополнительный код или другие компоненты, если есть
            }
        }}

    @Composable
    fun menubootom(){

        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp
        val smallestScreenWidthDp = configuration.smallestScreenWidthDp
        val isTablet = smallestScreenWidthDp >= 600
        val fontSize = if (isTablet) 50.sp else 30.sp
        //val configuration = LocalConfiguration.current
        val screenHeightDp = configuration.screenHeightDp

        val rowHeight = if (screenHeightDp <= 900) {
            140.dp // Высота для телефона
        } else {
            300.dp // Высота для планшета
        }
        val clickedState = remember { mutableStateOf(false) }
        var score by remember { mutableStateOf(0) }
        var backgroundColor by remember { mutableStateOf(Color.Blue) }
        val cornerRadius = if (isTablet) 70.dp else 30.dp // Задайте желаемый радиус закругления



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.BottomCenter
        ) {



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isTablet) 150.dp else 90.dp)
                    .background(Color.White)

            ) {


                Button(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 0.dp)
                        .wrapContentHeight() // Изменен модификатор на wrapContentHeight
                        .weight(1f)
                        .background(Color.Blue),
                    shape = RoundedCornerShape(0.dp),
                    onClick = {
                        val intent = Intent(this@MainActivity, SecondActiviti::class.java)
                        startActivity(intent)
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.boom),
                            contentDescription = "Nothing",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Button(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 0.dp)
                        .wrapContentHeight() // Изменен модификатор на wrapContentHeight
                        .weight(1f)
                        .background(Color.Blue),
                    shape = RoundedCornerShape(0.dp),
                    onClick = {
                        val intent = Intent(this@MainActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.target),
                            contentDescription = "Nothing",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Button(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 0.dp)
                        .wrapContentHeight() // Изменен модификатор на wrapContentHeight
                        .weight(1f)
                        .background(Color.Blue),
                    shape = RoundedCornerShape(0.dp),
                    onClick = {

                    }
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tetris2),
                            contentDescription = "Nothing",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Button(
                    colors = ButtonDefaults.buttonColors(Color.White),
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 0.dp)
                        .wrapContentHeight() // Изменен модификатор на wrapContentHeight
                        .weight(1f)
                        .background(Color.Blue),
                    shape = RoundedCornerShape(0.dp),
                    onClick = {
                        val intent = Intent(this@MainActivity, Main_menu::class.java)
                        startActivity(intent)
                    }
                ) {
                    Box(
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img),
                            contentDescription = "Nothing",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }


        }
    }

}

    @Composable
    fun WaterSplashAnimation() {
        val infiniteTransition = rememberInfiniteTransition()

        val circleSize by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 200f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Reverse
            )
        )

        val alpha by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1000
                    0.8f at 400
                    1f at 500
                    0f at 1000
                },
                repeatMode = RepeatMode.Reverse
            )
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.Blue)
                .clip(CircleShape)
                .graphicsLayer(
                    scaleX = circleSize,
                    scaleY = circleSize,
                    alpha = alpha
                )
        )
    }











