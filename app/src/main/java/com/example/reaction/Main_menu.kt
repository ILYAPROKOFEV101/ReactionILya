package com.example.reaction


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.example.reaction.DBrealtime.User
import com.example.reaction.DBrealtime.usersRef
import com.example.reaction.first.profile.ProfileIcon
import com.example.reaction.first.profile.ProfileName
import com.example.reaction.first.profile.sing_in.GoogleAuthUiClient
import com.example.reaction.first.profile.sing_in.tester
import com.example.reaction.first.profile.test
import com.example.reaction.logik.PreferenceHelper
import com.example.reaction.logik.USERRESULT
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
class Main_menu : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                LoginMenus()
                    //   levelskill()
                @Composable
                fun UserItem(user: User) {

                    Spacer(modifier = Modifier.height(15.dp))

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(65.dp)
                        //      .padding(start = 2.dp, end = 2.dp)
                        .clip(RoundedCornerShape(40.dp))

                    ){

                        Row(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()) {
                            val imageUrl = user.profilePictureUrl

                            imageUrl?.let {
                                Box(modifier = Modifier
                                    .size(60.dp)) {
                                    val painter = rememberImagePainter(imageUrl)

                                    Image(
                                        painter = painter,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(55.dp)
                                            .fillMaxSize()
                                            .align(Alignment.Center)
                                            .clip(CircleShape)
                                    )
                                }
                            }
                            Column(modifier = Modifier.fillMaxWidth()) {
                                // Text(text = "$userId")
                                // Text(text = "ID: $userId")
                                // Text(text = "Profile Picture URL: ${user.profilePictureUrl}")

                                Text(text = " ${user.username}", modifier = Modifier.fillMaxWidth(), style = TextStyle(fontSize = 24.sp))
                                Row(modifier = Modifier
                                    .height(40.dp)
                                    .fillMaxWidth()) {
                                    Text(text = stringResource(id = R.string.reactionresult), modifier = Modifier, style = TextStyle(fontSize = 24.sp))


                                        Text(text = " ${user.result}",  style = TextStyle(fontSize = 24.sp), color = Color.Blue,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth())


                                }


                            }

                        }
                    }

                }




                val users = remember { mutableStateListOf<User>() }

                usersRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        users.clear()
                        dataSnapshot.children.forEach { child ->
                            val user = child.getValue(User::class.java)
                            if (user != null) {
                                user.id = child.key // Устанавливаем ключ в качестве ID пользователя
                                users.add(user)
                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Обработка ошибок
                    }
                })
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        ,
                    contentAlignment = Alignment.TopCenter
                ) {
                    LazyColumn {
                        items(users) { user ->
                            UserItem(user = user)
                        }
                    }
                }
                BottomNavigationBar()

            }
        }
    }


    @Composable
    fun BottomNavigationBar() {

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
                        val intent = Intent(this@Main_menu, SecondActiviti::class.java)
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
                        val intent = Intent(this@Main_menu, MainActivity::class.java)
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

    @Preview(showBackground = true)



    @Preview(showBackground = true)
    @Composable


    fun LoginMenus() {

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
        var login by remember {
            mutableStateOf(false)
        }

        val showElement = PreferenceHelper.shouldShowElement(this)


        Row(modifier = Modifier.fillMaxWidth()) {


            Column(
                modifier = Modifier

                    .height(100.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()

                ) {

                    if (showElement == false) {
                        // Переменная show равна false
                        Text(
                            text = "LogIn",
                            fontSize = fontSizes,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 20.dp)
                        )

                        IconButton(
                            onClick = {
                                val intent = Intent(this@Main_menu, tester::class.java)
                                startActivity(intent)
                            }, modifier = Modifier.padding(top = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Login,
                                contentDescription = "Login Icon",
                                tint = Color.Blue,
                                modifier = Modifier
                                    .size(50.dp)
                                    .fillMaxHeight()
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.atantion),
                            style = MaterialTheme.typography.bodyLarge, fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .align(Alignment.CenterVertically)
                        )

                    } else {

                Box(modifier = Modifier.padding(top = 10.dp, start = 5.dp)) {
                    ProfileIcon(
                        userData = googleAuthUiClient.getSignedInUser(),
                        onSignOut = {
                            lifecycleScope.launch {
                                //   googleAuthUiClient.signOut()
                                // navController.popBackStack()
                            }
                        }
                    )
                }

                        val minSec = intent.getLongExtra("minSec", 0L)
                        val minMil = intent.getLongExtra("minMil", 0L)

                        test(
                                userData = googleAuthUiClient.getSignedInUser(), userresult = USERRESULT(((257..1000).random()).toInt())
                            )



                        Column(modifier = Modifier.fillMaxSize()) {

                            Box(modifier = Modifier.padding(start = 20.dp)) {


                                ProfileName(
                                    userData = googleAuthUiClient.getSignedInUser(),
                                    onSignOut = {
                                        lifecycleScope.launch {
                                            //   googleAuthUiClient.signOut()
                                            // navController.popBackStack()
                                        }
                                    }
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.Center
                            ) {


                                Text(
                                    text = stringResource(id = R.string.logout),
                                    fontSize = fontSizes,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                )
                                IconButton(
                                    onClick = {
                                        val intent = Intent(this@Main_menu, tester::class.java)
                                        startActivity(intent)
                                    }, modifier = Modifier
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Login,
                                        contentDescription = "Login Icon",
                                        tint = Color.Blue,
                                        modifier = Modifier
                                            .size(50.dp)
                                            .fillMaxHeight()
                                    )


                                }
                            }
                        }
                    }
                }


            }

        }
    }
}






