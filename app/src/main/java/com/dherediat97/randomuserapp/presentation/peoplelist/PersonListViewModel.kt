package com.dherediat97.randomuserapp.presentation.peoplelist

import androidx.lifecycle.ViewModel
import com.dherediat97.randomuserapp.domain.model.Person
import com.dherediat97.randomuserapp.domain.repository.PeopleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class PersonListViewModel : ViewModel() {
    private val peopleRepository = PeopleRepository()
    private val _state = MutableStateFlow(PersonUiState())
    val uiState: StateFlow<PersonUiState>
        get() = _state


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


    data class PersonUiState(
        val personList: List<Person> = emptyList(),
        val isLoading: Boolean = false,
        val isError: Boolean = false
    )
}