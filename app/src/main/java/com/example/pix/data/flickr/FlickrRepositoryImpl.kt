package com.example.pix.data.flickr

import com.example.pix.data.flickr.mapper.toEntity
import com.example.pix.domain.entity.Picture
import kotlin.random.Random

class FlickrRepositoryImpl(
    private val flickrApi: FlickrApi,
) : FlickrRepository {
    override suspend fun search(
        text: String,
        page: Int,
        count: Int
    ): Result<List<Picture>> = runCatching {
        val result = flickrApi.search(text, page, count)
        return Result.success(result.photos!!.photo.map {
            it.toEntity("q")
        })
    }
}