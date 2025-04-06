package com.example.pix.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pix.domain.entity.Picture
import com.example.pix.domain.usecase.SavePictureUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

@HiltViewModel(assistedFactory = DetailedPictureViewModel.DetailedPictureViewModelFactory::class)
class DetailedPictureViewModel @AssistedInject constructor(
    @Assisted val picture: Picture,
    private val savePictureUseCase: SavePictureUseCase
) :
    ViewModel() {
    @AssistedFactory
    interface DetailedPictureViewModelFactory {
        fun create(movieId: Picture): DetailedPictureViewModel
    }

    private val _isDownloadSuccess: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isDownloadSuccess: StateFlow<Boolean?> = _isDownloadSuccess


    fun savePicture(context: Context) {
        val fileName =
            if (picture.title.isBlank()) "Unknown Image.jpg" else picture.title.replace(
                "\\s".toRegex(),
                ""
            ) + ".jpg"
        viewModelScope.launch {
            val result: Result<File> = savePictureUseCase(context, picture.url, fileName)
            result.fold(
                onSuccess = {
                    _isDownloadSuccess.value = true
                },
                onFailure = {
                    _isDownloadSuccess.value = false
                }
            )
        }
    }
}