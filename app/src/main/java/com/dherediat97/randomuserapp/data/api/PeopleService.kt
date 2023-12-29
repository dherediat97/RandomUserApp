package com.dherediat97.randomuserapp.data.api

import com.dherediat97.randomuserapp.domain.usecases.multiplepersons.ResponseMultiplePeople
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleService {


    //Get multiple person sized by query "results" parameter
    @GET("api/")
    suspend fun getMultiplePerson(
        @Query("results") results: Number,
        @Query("nat") nat: String = "es",
//        @Query("page") page: Number? = 1,
//        @Query("seed") seed: String? = ""
    ): ResponseMultiplePeople

    @GET("api/")
    suspend fun getSinglePerson(
        @Query("uuid") uuid: String,
        @Query("results") results: Number = 1,
    ): ResponseMultiplePeople
}