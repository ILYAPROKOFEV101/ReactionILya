package com.example.reaction.first.profile.sing_in


data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val profilePictureUrl: String?,

   // val First_result: Int?,
   // val Second_result: Int?,
   // val Three_result: Int?


)

