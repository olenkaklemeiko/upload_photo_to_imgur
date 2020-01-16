package com.test.testapplication.di

import androidx.lifecycle.ViewModelProvider
import com.test.testapplication.application.factory.ViewModelFactory
import com.test.testapplication.application.fragment.links.LinksFragmentViewModel
import com.test.testapplication.application.fragment.main.MainFragmentViewModel
import com.test.testapplication.common.DataSourceInterface
import com.test.testapplication.common.bindViewModel
import com.test.testapplication.data.database.ImagesDatabase
import com.test.testapplication.data.network.Api
import com.test.testapplication.data.network.RetrofitProvider
import com.test.testapplication.datasource.ImageDataSource
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton

val dataSourceModule = Kodein.Module("dataSourceModule") {
    bind<DataSourceInterface>() with singleton { ImageDataSource(instance()) }
    bind<ImageDataSource.Factory>() with singleton { ImageDataSource.Factory(instance()) }
}

val viewModelModule = Kodein.Module("viewModelModule") {
    bind<ViewModelProvider.Factory>() with singleton {
        ViewModelFactory(
            kodein.direct
        )
    }
    bindViewModel<MainFragmentViewModel>() with provider {
        MainFragmentViewModel(
            instance(),
            instance(),
            instance()
        )
    }
    bindViewModel<LinksFragmentViewModel>() with provider { LinksFragmentViewModel(instance()) }
}

val networkModule = Kodein.Module("networkModule") {
    bind<Api>() with singleton {
        RetrofitProvider.retrofitInstance().create(Api::class.java)
    }
}

val localModule = Kodein.Module("localModule") {
    bind<ImagesDatabase>() with singleton { ImagesDatabase.getDatabase(instance()) }
}