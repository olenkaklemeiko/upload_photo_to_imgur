package com.test.testapplication.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "imageLink")
data class ImageLink (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val link : String
)