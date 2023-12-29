package com.dherediat97.randomuserapp.presentation

sealed class Screen(val route: String) {
    object Detail : Screen("personDetail/${NavArgs.Main.key}") {
        fun createRoute() = "personDetail/"
    }

    object Main : Screen(NavArgs.Main.key) {
        fun createRoute() = "/"
    }
}

enum class NavArgs(val key: String) {
    Main("/"),
    PersonDetail("personDetail/")
}