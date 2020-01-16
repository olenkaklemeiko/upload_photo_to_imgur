package com.test.testapplication.data.network

import com.test.testapplication.data.network.image.ImageUploadResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

interface Api {

    @Headers("Accept: application/json")
    @Multipart
    @POST("/3/upload")
    fun imageUpload(
        @Header("Authorization") clientID: String,
        @Part("image") file: RequestBody,
        @Part("name") name: RequestBody
    ): Single<ImageUploadResponse>
}