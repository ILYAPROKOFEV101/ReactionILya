package com.example.reaction.DBrealtime

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.Exclude
import com.google.firebase.database.FirebaseDatabase

data class User(
    @Exclude var id: String? = null,
    var profilePictureUrl: String? = null,
    var username: String? = null,
    var result: Int? = null
){


}
val database = FirebaseDatabase.getInstance("https://reaction-ba4ab-default-rtdb.europe-west1.firebasedatabase.app/")
val usersRef = database.getReference("users")

val userId = FirebaseAuth.getInstance().currentUser?.uid



