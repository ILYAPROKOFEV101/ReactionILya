package com.example.reaction

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class LogekLogIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<TiltViewModel>()
            TiltBar(viewModel)
                }
            }

    @Composable
    fun TiltBar(viewModel: TiltViewModel) {
        val tiltAngle by viewModel.tiltAngle.collectAsState()
        var previousTiltAngle by remember { mutableStateOf(tiltAngle) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            val barWidth = 10.dp
            val barHeight = 25.dp

            val xOffset = (tiltAngle / 90f) * (barHeight / 2)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp)
                    .height(barHeight)
                    .background(Color.White)
                    .drawWithContent {
                        val degrees = tiltAngle

                        val radians = Math.toRadians(degrees.toDouble()).toFloat()


                        val centerX = size.width / 2f
                        val centerY = size.height / 2f

                        if (previousTiltAngle == 0f && tiltAngle != 0f) {
                            // Выполните здесь необходимые действия для элемента с тоновой позиции 0
                        }

                        // Поворот на заданный угол
                        rotate(degrees + 270, pivot = Offset(centerX, centerY)) {
                            // Рисование содержимого Box
                            drawRect(Color.Red, size = size)
                        }
                    }
            )
            var rDate =  tiltAngle - 90
            Text(
                text = "${rDate.toInt()}°",
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            // Обновление предыдущего значения tiltAngle
            LaunchedEffect(tiltAngle) {
                previousTiltAngle = tiltAngle
            }
        }
    }

}
class TiltViewModel(application: Application) : AndroidViewModel(application) {
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val _tiltAngle = MutableStateFlow(0f)
    val tiltAngle: StateFlow<Float> = _tiltAngle

    private val sensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val angle = Math.toDegrees(Math.atan2(y.toDouble(), x.toDouble())).toFloat()
            _tiltAngle.value = angle
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    init {
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(sensorEventListener)
    }
}



























/*
class LogekLogIn : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            ComposeGoogleSignInCleanArchitectureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if(googleAuthUiClient.getSignedInUser() != null) {
                                    navController.navigate("profile")
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if(result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if(state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    navController.navigate("profile")
                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable("profile") {
                            ProfileScreen(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.popBackStack()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

*/























/*
class LogekLogIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {





        }
    }








    // logInto()
    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    fun logInto(){
        val configuration = LocalConfiguration.current

        val screenWidth = configuration.screenWidthDp.dp
        val smallestScreenWidthDp = configuration.smallestScreenWidthDp
        val isTablet = smallestScreenWidthDp >= 600
        val fontSize = if (isTablet) 50.sp else 30.sp
        //val configuration = LocalConfiguration.current
        val screenHeightDp = configuration.screenHeightDp

        val clickedState = remember { mutableStateOf(false) }
        var score by remember { mutableStateOf(0) }
        var backgroundColor by remember { mutableStateOf(Color.Blue) }
        val font = if(isTablet) 35.sp else 24.sp
        val cornerRadius = if (isTablet) 20.dp else 10.dp // Задайте желаемый радиус закругления


        var texFieldState by remember {
            mutableStateOf("")
        }




        Box(modifier = Modifier
            .fillMaxSize().padding(top = 100.dp),

            contentAlignment = Alignment.TopCenter
        ){
            Column(modifier = Modifier
                .height(500.dp)
                .width(400.dp)
                .padding(start = 20.dp, end = 20.dp),

                verticalArrangement = Arrangement.Top,


                ) {

                Text(text = stringResource(id = R.string.username ),
                    fontSize = font,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 75.dp,top = 30.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
                var textFieldState by remember {
                    mutableStateOf("")
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(9.dp)

                        .clip(RoundedCornerShape(20.dp))

                        .clickable {

                        },


                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {


                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Account",

                                tint = Color.Black,
                                modifier = Modifier.size(50.dp)
                            )

                        }




                        TextField(
                            value = texFieldState,
                            onValueChange = {

                            },
                            label = {
                                Text(text = "Name...")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.White
                            )
                        )
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(9.dp)

                        .clip(RoundedCornerShape(20.dp))

                        .clickable {

                        },


                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        TextField(
                            value = "",
                            onValueChange = {

                            },
                            label = {
                                Text(text = "Password...", style = MaterialTheme.typography.bodyLarge)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.White
                            )
                        )
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .background(Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {


                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Account",

                                tint = Color.Black,
                                modifier = Modifier.size(50.dp)
                            )

                        }
                    }
                }



            }
        }

    }*/

//}
