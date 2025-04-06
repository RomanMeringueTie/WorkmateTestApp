package com.example.pix.domain.usecase

import android.content.Context
import android.graphics.Bitmap.CompressFormat.JPEG
import coil3.Bitmap
import coil3.BitmapImage
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import java.io.File
import java.io.FileOutputStream

class SavePictureUseCaseImpl() : SavePictureUseCase {
    override suspend fun invoke(
        context: Context,
        imageUrl: String,
        fileName: String
    ): Result<File> {
        val imageLoader = ImageLoader(context)

        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = imageLoader.execute(request)

        val image = (result as? SuccessResult)?.image as? BitmapImage ?: return Result.failure(
            Throwable("Unknown Error")
        )

        val bitmap: Bitmap = image.bitmap

        val externalDir = context.getExternalFilesDir(null) ?: return Result.failure(
            Throwable("Unknown Error")
        )

        val file = File(externalDir, fileName)
        FileOutputStream(file).use { out ->
            Bitmap.createBitmap(bitmap).compress(JPEG, 100, out)
        }

        return Result.success(file)

    }
}