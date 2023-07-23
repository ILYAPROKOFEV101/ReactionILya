package com.example.reaction.ui.theme

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reaction.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction0
import com.example.reaction.MainActivity

class FirsPage(openSecondActivity: KFunction0<Unit>) {
    companion object {
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

            Box(modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.TopCenter
            ){


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
                            text = if (clicked) "$sumS: $suMil" else " result: $sumS:$suMil",

                            color = Color.White,
                            fontSize = 30.sp,

                            //textAlign = TextAlign.C
                        )
                        Text(
                            text = "Minimum: $minSec:$minMil",
                            color = Color.White,
                            fontSize = 30.sp,
                        )

                    }


                }









                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(Color.White)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.End
                ) {

                    fun FirsPage(openSecondActivity: () -> Unit) {

                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(Color.White),
                        modifier = Modifier
                            .background(Color.White)
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.Blue),
                        shape = RoundedCornerShape(0.dp),
                        onClick = {}
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.target),
                            contentDescription = "Nothing",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                            // .size(64.dp)
                            // .clip(CircleShape)
                        )
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(Color.White),
                        modifier = Modifier
                            .background(Color.White)
                            .weight(1f)
                            .fillMaxHeight(),
                        //  .background(Color.Blue),
                        shape = RoundedCornerShape(0.dp),
                        onClick = {}
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.target),
                            contentDescription = "Nothing",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                            // .size(64.dp)
                            // .clip(CircleShape)
                        )
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White)
                            .fillMaxHeight()
                            .background(Color.White),
                        shape = RoundedCornerShape(0.dp),
                        onClick = {}
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.target),
                            contentDescription = "Nothing",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                            // .size(64.dp)
                            // .clip(CircleShape)
                        )

                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(Color.White),
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White)
                            .fillMaxHeight()
                            .background(Color.Blue),
                        shape = RoundedCornerShape(0.dp),
                        onClick = {}
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.target),
                            contentDescription = "Nothing",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                            // .size(64.dp)
                            // .clip(CircleShape)
                        )
                    }
                }

            }
        }



        // Другие функции, если необходимо
    }
}
