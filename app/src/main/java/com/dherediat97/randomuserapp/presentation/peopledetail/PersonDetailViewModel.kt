package com.dherediat97.randomuserapp.presentation.peopledetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dherediat97.randomuserapp.domain.model.Person
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonDetailViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterUiState())
    val uiState: StateFlow<CharacterUiState>
        get() = _state

    fun addPerson(newPerson: Person) {
        _state.update { it.copy(person = newPerson) }
    }

    data class CharacterUiState(
        val person: Person? = null
    )
}