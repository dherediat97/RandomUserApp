package com.dherediat97.randomuserapp.presentation.peoplelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PersonListViewModel(
    application: Application,
    val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {


    private val personEmail: String? = savedStateHandle["personEmail"]

    init {
        println("personEmail=$personEmail")
    }

    //People Repository
    private val peopleRepository = PeopleRepository()

    //Internal UiState
    private val _state = MutableStateFlow(PersonsUiState())

    //Public UiState
    val uiState: StateFlow<PersonsUiState>
        get() = _state

    //Fetch multiple persons using a results size number
    suspend fun fetchMultiplePersons(results: Number) {
        runCatching {
            _state.update { it.copy(isLoading = true) }

            val persons = peopleRepository.getMultiplePerson(results = results)

            _state.update {
                it.copy(personList = persons, isLoading = false)
            }
            _state.update { it.copy(isError = false) }
        }.onFailure {
            _state.update { it.copy(isError = true) }
        }
    }

    fun getUser(): Person? {
        return uiState.value.personList.find { it.email == savedStateHandle["personEmail"] }
    }

    //Internal data class for person list data
    data class PersonsUiState(
        val personList: List<Person> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )
}