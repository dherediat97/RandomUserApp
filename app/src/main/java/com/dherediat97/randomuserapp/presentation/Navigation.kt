package com.dherediat97.randomuserapp.presentation

import com.dherediat97.randomuserapp.domain.model.Person

sealed class Screen(val route: String) {
    object Main : Screen(NavArgs.Main.key) {
        fun createRoute() = "/"
    }

    object Detail : Screen(NavArgs.PersonDetail.key) {
        fun createRoute(person: Person) = "personDetail/personEmail=${person.email}"
    }

    object Search : Screen(NavArgs.Search.key) {
        fun createRoute() = "search/"
    }
}

enum class NavArgs(val key: String) {
    Main("/"),
    PersonDetail("personDetail/personEmail={personEmail}"),
    Search("search/")
}