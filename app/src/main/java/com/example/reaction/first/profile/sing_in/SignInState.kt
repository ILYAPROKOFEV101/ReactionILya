package com.example.reaction.first.profile.sing_in


data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
