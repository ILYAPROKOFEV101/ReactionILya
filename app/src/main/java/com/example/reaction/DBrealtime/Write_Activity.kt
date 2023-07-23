package com.example.reaction.DBrealtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class Write_Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)







        setContent {
            @Composable
            fun UserItem(user: User) {
                Spacer(modifier = Modifier.height(15.dp))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(start = 2.dp, end = 2.dp)
                    .clip(RoundedCornerShape(90.dp))
                    .border(2.dp, Color.Black)
                    ){

                    Row(modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()) {
                        val imageUrl = user.profilePictureUrl

                        imageUrl?.let {
                            Box(modifier = Modifier
                                .fillMaxHeight()
                                .width(80.dp)) {
                                val painter = rememberImagePainter(imageUrl)

                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    modifier = Modifier
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
                                Text(text = " Total Result : ", modifier = Modifier, style = TextStyle(fontSize = 24.sp))
                                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.Red))
                                {
                                        Text(text = "null" , style = TextStyle(fontSize = 9.sp), color = Color.Blue,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth())
                                }
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
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                LazyColumn {
                    items(users) { user ->
                        UserItem(user = user)
                    }
                }
            }
        }

    }

}
