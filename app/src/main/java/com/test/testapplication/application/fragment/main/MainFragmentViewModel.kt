package com.test.testapplication.application.fragment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.test.testapplication.common.DataSourceInterface
import com.test.testapplication.data.database.ImagesDatabase
import com.test.testapplication.data.database.entity.ImageLink
import com.test.testapplication.data.dto.Image
import com.test.testapplication.data.network.Api
import com.test.testapplication.data.network.image.ImageUploadResponse
import com.test.testapplication.datasource.ImageDataSource
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody

class MainFragmentViewModel(
    private val dataSourceFactory: ImageDataSource.Factory,
    private val database: ImagesDatabase,
    private val api: Api
) : ViewModel() {

    private val imageLinkDao = database.getImageLinkDao()

    fun getAllImages(): LiveData<PagedList<Image>> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            val config = PagedList.Config.Builder()
                .setPageSize(30)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build()

            val imagesList = LivePagedListBuilder<Int, Image>(
                dataSourceFactory, config
            ).build()

            emitSource(imagesList)
        }
    }

    fun uploadImages(image: RequestBody, name: RequestBody): Single<ImageUploadResponse> {
        val clientID = "Client-ID 99e50c5ce1493f5"
        return api.imageUpload(clientID, image, name)
    }

    fun saveLink(response: ImageUploadResponse) {
        imageLinkDao.insert(ImageLink(null, response.data.link))
    }
}
