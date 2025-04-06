package com.example.pix.di

import com.example.pix.data.flickr.FlickrApi
import com.example.pix.data.flickr.FlickrRepository
import com.example.pix.data.flickr.FlickrRepositoryImpl
import com.example.pix.data.flickr.FlickrRetrofit
import com.example.pix.domain.usecase.GetPicturesUseCase
import com.example.pix.domain.usecase.GetPicturesUseCaseImpl
import com.example.pix.domain.usecase.SavePictureUseCase
import com.example.pix.domain.usecase.SavePictureUseCaseImpl
import com.example.pix.presentation.GridPicturesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule() {

    @Provides
    fun provideFlickrApi(): FlickrApi {
        return FlickrRetrofit.api
    }

    @Provides
    fun provideFlickrRepository(flickrApi: FlickrApi): FlickrRepository {
        return FlickrRepositoryImpl(flickrApi)
    }

    @Provides
    fun provideGetPicturesUseCase(flickrRepository: FlickrRepository): GetPicturesUseCase {
        return GetPicturesUseCaseImpl(flickrRepository)
    }

    @Provides
    fun provideSavePicturesUseCase(): SavePictureUseCase {
        return SavePictureUseCaseImpl()
    }

    @Provides
    fun provideGridPictureViewModel(getPicturesUseCase: GetPicturesUseCase): GridPicturesViewModel {
        return GridPicturesViewModel(getPicturesUseCase)
    }
}