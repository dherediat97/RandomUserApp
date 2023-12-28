package com.dherediat97.randomuserapp.domain.model

import android.graphics.Picture
import android.location.Location

data class Person(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
//    val login: Login,
//    val dob: Dob,
//    val registered: Registered,
    val phone: String,
    val cell: String,
//    val id: Id,
    val picture: Picture,
    val nat: String
)