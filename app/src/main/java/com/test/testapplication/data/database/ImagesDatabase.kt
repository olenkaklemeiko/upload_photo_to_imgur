package com.test.testapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.testapplication.data.database.dao.ImageLinkDao
import com.test.testapplication.data.database.entity.ImageLink

@Database(
    entities = [ImageLink::class], version = 1, exportSchema = false
)
abstract class ImagesDatabase : RoomDatabase() {

    abstract fun getImageLinkDao(): ImageLinkDao

    companion object {
        @Volatile
        private var INSTANCE: ImagesDatabase? = null

        fun getDatabase(context: Context): ImagesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance =
                    Room.databaseBuilder(
                        context.applicationContext,
                        ImagesDatabase::class.java,
                        "imagesDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}