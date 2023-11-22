package com.example.innholandclientt

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Login : NavigationItem("login", androidx.core.R.drawable.ic_call_decline, "Login")
    object Register : NavigationItem("register", androidx.core.R.drawable.ic_call_answer, "Register")
}