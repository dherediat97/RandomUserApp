package com.dherediat97.randomuserapp.presentation.peoplelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.domain.repository.PeopleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonListViewModel(
    application: Application,
    val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    //People Repository
    private val peopleRepository = PeopleRepository()

    //Internal UiState
    private val _state = MutableStateFlow(PersonsUiState())

    private val _searchState = MutableStateFlow(PersonsSearchUiState())

    private val imageList = mutableListOf<String>()

    private var page = 1
    private var results = 20

    val MAX_RESULTS_API_SIZE = 190

    //Public UiState
    val uiState: StateFlow<PersonsUiState>
        get() = _state

    val searchUiState: StateFlow<PersonsSearchUiState>
        get() = _searchState

    //Fetch multiple persons using a results size number
    suspend fun fetchMultiplePersons() {
        runCatching {
            _state.update { it.copy(isLoading = true) }

            //First time, load 20 persons, the next times increments in 1 the page counter and 20
            //new persons were aggregated
            if (page >= 1 || _state.value.personList.isEmpty()) {
                val persons = peopleRepository.getMultiplePerson(results = results, page = page)
                val newPersons = mutableListOf<Person>()
                persons.forEach { person ->
                    val samePerson = imageList.find { it == person.picture.large }

                    if (samePerson == null) {
                        newPersons.add(person)
                        imageList.add(person.picture.large)
                    }
                }

                _state.update {
                    it.copy(
                        personList = uiState.value.personList + newPersons,
                        isLoading = false,
                        page = page,
                        results = results,
                        isError = false
                    )
                }
                _searchState.update {
                    it.copy(
                        persons = uiState.value.personList + newPersons,
                    )
                }
                page++
            }
        }.onFailure {
            _state.update { it.copy(isError = true) }
        }
    }

    fun filterByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.update { it.copy(isLoading = true) }
            val personsFindByName: List<Person> = uiState.value.personList.filter { person ->
                person.name.last.contains(name, true) || person.name.first.contains(name, true)
            }

            _searchState.update {
                it.copy(
                    isLoading = false,
                    searchQuery = name,
                    persons = personsFindByName,
                )
            }
        }
    }

    fun filterByEmail(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchState.update { it.copy(isLoading = true) }
            val personsFindByEmail: List<Person> = uiState.value.personList.filter { person ->
                person.email.contains(email, true)
            }
            _searchState.update {
                it.copy(
                    isLoading = false,
                    searchQuery = email,
                    persons = personsFindByEmail,
                )
            }
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

    //Internal data class for person list data
    data class PersonsSearchUiState(
        val persons: List<Person> = emptyList(),
        val searchQuery: String = "",
        val isLoading: Boolean = false,
    )
}