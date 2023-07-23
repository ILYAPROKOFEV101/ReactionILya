package com.example.reaction.first.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.reaction.R
import com.example.reaction.first.profile.sing_in.UserData
import com.example.reaction.logik.PreferenceHelper
import com.example.reaction.logik.USERRESULT
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))


        }

        if(userData?.username != null) {

            androidx.compose.material.Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))
                //   PreferenceHelper.setShowElement(context, false) // Скрыть элемент

        }

        androidx.compose.material.Button(onClick =  {
            onSignOut()
            PreferenceHelper.setShowElement(context, false)
        }) {

            androidx.compose.material.Text(text = stringResource(id = R.string.singout))
        }




    }
}





@Composable
fun ProfileIcon(userData: UserData?, onSignOut: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(90.dp)
                    .fillMaxHeight()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun ProfileName(userData: UserData?,
                onSignOut: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start

    ) {
        if (userData?.username != null) {

            androidx.compose.material.Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}



@Composable
fun test(userData: UserData?, userresult: USERRESULT?){

    val icon = userData?.profilePictureUrl
    val name =   userData?.username
    val id = userData?.userId
    val your = userresult?.result

   // val database = Firebase.database("https://reaction-ba4ab-default-rtdb.europe-west1.firebasedatabase.app/")
    val database = Firebase.database(stringResource(id = R.string.DataBase))
    val myRef = database.getReference("users").child(id!!)

    val values = mapOf(
        "username" to name,
        "profilePictureUrl" to icon,
        "result" to your
    )

    myRef.setValue(values)


}


@Composable
fun showUsersList() {
    // Получение ссылки на базу данных
    val database = Firebase.database(stringResource(id = R.string.DataBase))
    val usersRef = database.getReference("users")

    // Создание списка для хранения данных пользователей
    val usersList = mutableListOf<UserData>()

    // Добавление слушателя к usersRef
    usersRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Очистка списка
            usersList.clear()
            // Получение данных из dataSnapshot
            for (userSnapshot in dataSnapshot.children) {
                val userData = userSnapshot.getValue(UserData::class.java)
                if (userData != null) {
                    usersList.add(userData)
                }
            }
            // Обновление интерфейса с новым списком пользователей
            // ...
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Обработка ошибок
        }
    })
}
