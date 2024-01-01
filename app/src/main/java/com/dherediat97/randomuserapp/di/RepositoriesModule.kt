package com.dherediat97.randomuserapp.di

import com.dherediat97.randomuserapp.domain.repository.PeopleRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single { PeopleRepository() }
}