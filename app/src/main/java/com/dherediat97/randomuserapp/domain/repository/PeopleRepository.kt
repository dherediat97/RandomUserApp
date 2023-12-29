package com.dherediat97.randomuserapp.domain.repository

import com.dherediat97.randomuserapp.data.api.NetworkUtil
import com.dherediat97.randomuserapp.domain.model.Person

class PeopleRepository {

    private val peopleService = NetworkUtil.peopleService

    suspend fun getMultiplePerson(results: Number): List<Person> {
        val responseMultiplePeople = peopleService.getMultiplePerson(results)
        return responseMultiplePeople.results
    }

    suspend fun getSinglePerson(name: String): Person {
        val responseSinglePerson = peopleService.getSinglePerson(name)
        return responseSinglePerson.results[0]
    }
}