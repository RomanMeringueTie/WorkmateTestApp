package com.example.pix.domain.usecase

import android.content.Context
import java.io.File

interface SavePictureUseCase {
    suspend operator fun invoke(context: Context, imageUrl: String, fileName: String): Result<File>
}