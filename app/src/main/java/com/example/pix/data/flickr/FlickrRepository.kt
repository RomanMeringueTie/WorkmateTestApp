package com.example.pix.data.flickr

import com.example.pix.domain.entity.Picture

interface FlickrRepository {
    suspend fun search(
        text: String = "cats",
        page: Int = 1,
        count: Int = 100
    ): Result<List<Picture>>
}