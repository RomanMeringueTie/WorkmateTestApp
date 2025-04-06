package com.example.pix.domain.usecase

import com.example.pix.data.flickr.FlickrRepository
import com.example.pix.domain.entity.Picture

class GetPicturesUseCaseImpl(private val repository: FlickrRepository) : GetPicturesUseCase {
    override suspend fun invoke(): List<Picture> {
        return repository.search().fold(
            onSuccess = {
                return it
            },
            onFailure = {
                throw Exception(it)
            }
        )
    }
}