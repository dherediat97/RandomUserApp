package com.dherediat97.randomuserapp.presentation.peoplelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PersonListViewModel(
    application: Application,
    val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    //People Repository
    private val peopleRepository = PeopleRepository()

    //Internal UiState
    private val _state = MutableStateFlow(PersonsUiState())


    private var page = 1
    private var results = 20

    //Public UiState
    val uiState: StateFlow<PersonsUiState>
        get() = _state

    //Fetch multiple persons using a results size number
    suspend fun fetchMultiplePersons() {
        runCatching {
            _state.update { it.copy(isLoading = true) }

            //First time, load 50 persons, the next times increments in 1 the page counter and 50 new
            //persons were aggregated
            if (page >= 1 || _state.value.personList.isEmpty()) {
                val persons = peopleRepository.getMultiplePerson(results = results, page = page)
                _state.update {
                    it.copy(
                        personList = uiState.value.personList + persons,
                        isLoading = false,
                        page = page,
                        results = results,
                        isError = false
                    )
                }
                page++
            }
        }.onFailure {
            _state.update { it.copy(isError = true) }
        }
    }

    fun getUser(): Person {
        return uiState.value.personList.find { it.email == savedStateHandle["personEmail"] }!!
    }

    //Internal data class for person list data
    data class PersonsUiState(
        val personList: List<Person> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val page: Number = 1,
        val results: Number = 10
    )
}