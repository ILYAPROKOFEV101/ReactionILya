package com.example.reaction

//import com.example.ColorChanger


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


open class SecondActiviti : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var colors by remember {
                mutableStateOf(
                    mutableStateListOf(
                        Color.Red,

                        Color.Blue,
                        Color.Yellow

                    )
                )
            }

            LaunchedEffect(Unit) {
                while (true) {
                    colors.shuffle() // Перемешиваем список цветов
                    delay(300) // Пауза в 1 секунду
                }

            }
            bomb(colors)
                //  CountdownTimer(countdownSeconds = 60)
        }
    }



    var clicked by mutableStateOf(false)

    var over by mutableStateOf(false)

    var sumtimer by mutableStateOf(0)

    var bestresult by mutableStateOf(0)

    var bestresult2 by mutableStateOf(0)



    @Composable
    fun CountdownTimer(countdownSeconds: Int, ) {
        var secondsRemaining by remember { mutableStateOf(countdownSeconds) }
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp

        //  val isTablet = configuration.smallestScreenWidthDp >= 600

        //  val configuration = resources.configuration
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

        LaunchedEffect(Unit) {
            while (secondsRemaining > 0) {
                delay(1000)
                secondsRemaining--
            }
        }
            if(secondsRemaining <= 0){
                over = !over // true
            }

        sumtimer = secondsRemaining

        Text(
            text = "Time remaining: $secondsRemaining s",
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )


    }



        @SuppressLint("CoroutineCreationDuringComposition")
        @Composable
    fun bomb(colors: List<Color>, countdownSeconds: Int = 60) {


            var buttonColor by remember { mutableStateOf(Color.Green) }


            var green by remember {
                mutableStateOf(Color.Green)
            }
            var blue by remember {
                mutableStateOf(Color.Blue)

            }
            var red by remember {
                mutableStateOf(Color.Red)
            }
            var yellow by remember {
                mutableStateOf(Color.Yellow)
            }


            val configuration = LocalConfiguration.current
            val screenHeight = configuration.screenHeightDp.dp
            val screenWidth = configuration.screenWidthDp.dp

            //  val isTablet = configuration.smallestScreenWidthDp >= 600

            //  val configuration = resources.configuration
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

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 0.dp)

                    .padding(bottom = 0.dp),
                verticalArrangement = Arrangement.Center,

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clickable {
                            clickedState.value = !clickedState.value
                            bestresult = score



                            if (bestresult > 0) {
                                score = 0
                            }

                            if (bestresult > bestresult2)
                                bestresult2 = bestresult


                        }
                        .padding(
                            top = (screenHeight * 0.0f),
                            bottom = (screenHeight * 0.0f),
                            start = (screenWidth * 0.0f),
                            end = (screenWidth * 0.0f)
                        )
                        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))

                        .background(

                            if (sumtimer == 0) {
                                Color.White
                            } else if (clickedState.value) {
                                colors[0]
                            } else {
                                Color.White
                            }

                        )
                ) {

                    val context = LocalContext.current



                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 10.dp)
                            .padding(start = 20.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start

                    ) {
                        Text(
                            text = "${stringResource(id = R.string.bestresult)}: $bestresult2",
                            fontSize = fontSize,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "${stringResource(id = R.string.result)}: $score",
                            fontSize = fontSize,
                            textAlign = TextAlign.Center
                        )

                        if (clickedState.value) {
                            CountdownTimer(countdownSeconds = 60)
                        }

                    }
                }



                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = (screenWidth * 0.0f),
                            end = (screenWidth * 0.0f),
                            bottom = if (isTablet) (screenHeight * 0.126f) else (screenHeight * 0.097f)
                        )
                        .height(rowHeight)
                        .background(green),

                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    var backgroundColor by remember { mutableStateOf(Color.Blue) }

                    var isScoreIncrementAllowed = true

                    Button(
                        colors = ButtonDefaults.buttonColors(red),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                            .border(
                                width = 10.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(cornerRadius)
                            )
                            .background(
                                if (sumtimer == 0) {
                                    Color.White
                                } else if (clickedState.value) {
                                    colors[0]
                                } else {
                                    Color.White
                                }
                            )

                            .fillMaxHeight()
                            .clip(
                                if (isTablet) RoundedCornerShape(70.dp) else RoundedCornerShape(30.dp)
                            ),
                        onClick = {

                            if (colors[0] == Color.Red) {

                               if(clickedState.value) {


                                   score += 1

                                   if(score > 140) {
                                       score -= 1
                                   }
                               }
                                if (sumtimer == 0) {
                                    score = 0
                                }
                                if (sumtimer == 60) {
                                    score = 0
                                }

                            }
                        }
                    ) {

                    }


                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                            .border(
                                width = 10.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(cornerRadius)
                            )
                            .background(
                                if (sumtimer == 0) {
                                    Color.White
                                } else if (clickedState.value) {
                                    colors[0]
                                } else {
                                    Color.White
                                }
                            )

                            .fillMaxHeight()
                            .clip(
                                if (isTablet) RoundedCornerShape(70.dp) else RoundedCornerShape(30.dp)
                            ),

                        onClick = {
                            if (colors[0] == Color.Blue) {
                                if(clickedState.value) {


                                    score += 1

                                    if(score > 140) {
                                        score -= 1
                                    }
                                }
                                if (sumtimer == 0) {
                                    score = 0
                                }
                                if (sumtimer == 60) {
                                    score = 0
                                }
                            }
                        }
                    ) {
                        // Содержимое второй кнопки
                    }


                    Button(
                        colors = ButtonDefaults.buttonColors(yellow),
                        shape = RoundedCornerShape(0.dp),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                            .border(
                                width = 10.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(cornerRadius)
                            )

                            .background(
                                if (sumtimer == 0) {
                                    Color.White
                                } else if (clickedState.value) {
                                    colors[0]
                                } else {
                                    Color.White
                                }
                            )

                            .fillMaxHeight()
                            .clip(
                                if (isTablet) RoundedCornerShape(70.dp) else RoundedCornerShape(30.dp)
                            ),
                        onClick = {
                            if (colors[0] == Color.Yellow) {

                                if(clickedState.value) {

                                    score += 1

                                    if(score > 140) {
                                        score -= 1
                                    }
                                }
                                // 300 миллисекунд = 0.3 секунды
                                if (sumtimer == 0) {
                                    score = 0
                                }
                                if (sumtimer == 60) {
                                    score -= 1
                                }
                            }
                        }
                    ) {
                        // Содержимое третьей кнопки
                    }
                }
            }

            //нижнии кнопки

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom
            ) {
                // Ваш код для остальной части экрана

                menubootom() // Вызов вашего bottombar() функции

                // Дополнительный код или другие компоненты, если есть
            }

        }

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
                        val intent = Intent(this@SecondActiviti, MainActivity::class.java)
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
                        val intent = Intent(this@SecondActiviti, Main_menu::class.java)
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



