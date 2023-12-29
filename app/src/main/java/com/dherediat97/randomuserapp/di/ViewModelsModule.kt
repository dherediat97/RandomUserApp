package com.dherediat97.randomuserapp.di

import com.dherediat97.randomuserapp.presentation.peopledetail.PersonDetailViewModel
import com.dherediat97.randomuserapp.presentation.peoplelist.PersonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        PersonListViewModel()
    }
    viewModel {
        PersonDetailViewModel()
    }
}