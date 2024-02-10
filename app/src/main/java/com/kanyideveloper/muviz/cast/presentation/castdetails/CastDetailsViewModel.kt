/*
 * Copyright 2024 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kanyideveloper.muviz.cast.presentation.castdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanyideveloper.muviz.cast.domain.usecase.GetCastDetailsUseCase
import com.kanyideveloper.muviz.common.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastDetailsViewModel @Inject constructor(
    private val getCastDetailsUseCase: GetCastDetailsUseCase,
) : ViewModel() {
    private val _castDetailsUiState = MutableStateFlow(CastDetailsUiState())
    val castDetailsUiState = _castDetailsUiState.asStateFlow()
    fun getCastDetails(id: Int) {
        viewModelScope.launch {
            _castDetailsUiState.update {
                it.copy(
                    isLoading = true
                )
            }
            when (val result = getCastDetailsUseCase(id)) {
                is Resource.Error -> {
                    _castDetailsUiState.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
                is Resource.Success -> {
                    _castDetailsUiState.update {
                        it.copy(
                            isLoading = false,
                            castDetails = result.data
                        )
                    }
                }
                else -> {
                    castDetailsUiState
                }
            }
        }
    }
}
