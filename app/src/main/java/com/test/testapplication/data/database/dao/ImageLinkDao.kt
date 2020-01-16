package com.test.testapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.testapplication.data.database.entity.ImageLink

@Dao
interface ImageLinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(link: ImageLink)

    @Query("SELECT imageLink.link FROM imageLink")
    fun getImagesLink(): List<String>

}