package com.dherediat97.randomuserapp.domain.usecases.multiplepersons

import com.dherediat97.randomuserapp.domain.model.Person

data class ResponseMultiplePeople(val results: MutableList<Person>, val info: Info)

data class Info(val seed: String, val results: Number, val page: Number, val version: String)