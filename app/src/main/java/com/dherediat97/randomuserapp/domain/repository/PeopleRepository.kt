package com.dherediat97.randomuserapp.domain.repository

import com.dherediat97.randomuserapp.data.api.NetworkUtil
import com.dherediat97.randomuserapp.domain.model.Person

class PeopleRepository {

    private val peopleService = NetworkUtil.peopleService

    suspend fun getMultiplePerson(results: Number, page: Number): MutableList<Person> {
        val responseMultiplePeople = peopleService.getMultiplePerson(results, page)
        return responseMultiplePeople.results
    }

}