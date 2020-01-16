package com.test.testapplication.common

import androidx.paging.PositionalDataSource
import com.test.testapplication.data.dto.Image

abstract class DataSourceInterface : PositionalDataSource<Image>() {
}