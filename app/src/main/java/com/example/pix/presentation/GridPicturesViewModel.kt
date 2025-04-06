package com.example.pix.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pix.domain.usecase.GetPicturesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GridPicturesViewModel @Inject constructor(private val getPicturesUseCase: GetPicturesUseCase) :
    ViewModel() {
    private val _state = MutableStateFlow(GridPicturesState())
    val state: StateFlow<GridPicturesState> = _state

    init {
        loadPictures()
    }

    private fun loadPictures() {
        if (_state.value.state == ScreenState.Loading) {
            viewModelScope.launch {
                try {
                    val result = getPicturesUseCase()
                    _state.value =
                        _state.value.copy(
                            state = ScreenState.Success(result),
                            isRefreshing = false
                        )
                } catch (e: Exception) {
                    _state.value =
                        _state.value.copy(
                            state = ScreenState.Failure(e.message),
                            isRefreshing = false
                        )

                }
            }
        } else
            return
    }

    fun onRetry() {
        _state.value = _state.value.copy(
            state = ScreenState.Loading,
            isRefreshing = true
        )
        loadPictures()
    }
}