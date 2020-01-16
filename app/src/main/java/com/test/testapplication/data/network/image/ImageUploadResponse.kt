package com.test.testapplication.data.network.image

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageUploadResponse(
    @SerializedName("data")
    @Expose
    val data: ResponseData,
    @SerializedName("success")
    @Expose
    val success: Boolean,
    @SerializedName("status")
    @Expose
    val status: Int
)
