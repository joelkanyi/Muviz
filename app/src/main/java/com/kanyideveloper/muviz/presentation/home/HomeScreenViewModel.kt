package com.kanyideveloper.muviz.presentation.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class HomeScreenViewModel @Inject constructor(

) : ViewModel() {
    private val _selectedOption = mutableStateOf("Movies")
    val selectedOption: State<String> = _selectedOption

    private val _selectedGenre = mutableStateOf("Drama")
    val selectedGenre: State<String> = _selectedGenre

    fun setSelectedOption(selectedOption: String) {
        _selectedOption.value = selectedOption
    }

    fun setGenre(genre: String) {
        _selectedGenre.value = genre
    }
}