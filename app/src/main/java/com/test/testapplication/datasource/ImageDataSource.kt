package com.test.testapplication.datasource

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import androidx.paging.DataSource
import com.test.testapplication.common.DataSourceInterface
import com.test.testapplication.data.dto.Image

class ImageDataSource(
    private val context: Context
) : DataSourceInterface() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Image>) {
        callback.onResult(
            getImageWithParam(
                params.requestedLoadSize,
                params.requestedStartPosition
            ), 0
        )
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Image>) {
        callback.onResult(getImageWithParam(params.loadSize, params.startPosition))
    }

    private fun getImageWithParam(limit: Int, offset: Int): MutableList<Image> {
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        )
        val cursor = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            MediaStore.Images.Media.DEFAULT_SORT_ORDER + " LIMIT " + limit + " OFFSET " + offset
        )

        val listOfAllImages: MutableList<Image> = mutableListOf()
        val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
        while (cursor.moveToNext()) {
            val absolutePathOfImage = cursor.getString(columnIndexData)
            listOfAllImages.add(Image(absolutePathOfImage))
        }
        return listOfAllImages
    }

    class Factory(private val context: Context) : DataSource.Factory<Int, Image>() {
        override fun create(): DataSource<Int, Image> {
            return ImageDataSource(context)
        }
    }
}