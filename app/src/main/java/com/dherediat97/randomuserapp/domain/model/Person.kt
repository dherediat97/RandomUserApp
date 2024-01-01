package com.dherediat97.randomuserapp.domain.model


data class Person(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val registered: Registered,
    val phone: String,
    val cell: String,
    val picture: Picture,
    val nat: String
)
