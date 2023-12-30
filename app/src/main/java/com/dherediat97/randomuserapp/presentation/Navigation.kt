package com.dherediat97.randomuserapp.presentation

import com.dherediat97.randomuserapp.domain.model.Person

sealed class Screen(val route: String) {
    object Detail : Screen("personDetail/personEmail={personEmail}") {
        fun createRoute(person: Person) = "personDetail/personEmail=${person.email}"
    }

    object Main : Screen(NavArgs.Main.key) {
        fun createRoute() = "/"
    }
}

enum class NavArgs(val key: String) {
    Main("/"),
    PersonDetail("personDetail/personEmail={personEmail}")
}