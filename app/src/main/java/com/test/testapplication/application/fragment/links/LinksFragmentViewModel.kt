package com.test.testapplication.application.fragment.links

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.test.testapplication.data.database.ImagesDatabase
import kotlinx.coroutines.Dispatchers

class LinksFragmentViewModel(
    database: ImagesDatabase
) : ViewModel() {

    private val imageLinkDao = database.getImageLinkDao()

    fun getLinksList(): LiveData<List<String>> {
        return liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(imageLinkDao.getImagesLink())
        }
    }
}
