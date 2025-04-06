package com.example.pix.domain.usecase

import com.example.pix.domain.entity.Picture

interface GetPicturesUseCase {
    suspend operator fun invoke(): List<Picture>
}