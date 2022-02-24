package com.kanyideveloper.muviz.screens.home

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.kanyideveloper.muviz.data.repository.FilmsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val filmsRepository: FilmsRepository
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