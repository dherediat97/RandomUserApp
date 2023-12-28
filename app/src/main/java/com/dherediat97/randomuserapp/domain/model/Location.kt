package com.dherediat97.randomuserapp.domain.model


//Main Object
data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postCode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
)


//Internal Objects
data class Street(val number: Number, val name: String)

data class Coordinates(val latitude: String, val longitude: String)

data class Timezone(val offtset: String, val description: String)