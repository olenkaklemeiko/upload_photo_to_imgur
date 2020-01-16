package com.test.testapplication.application

import android.app.Application
import com.test.testapplication.di.dataSourceModule
import com.test.testapplication.di.localModule
import com.test.testapplication.di.networkModule
import com.test.testapplication.di.viewModelModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule

class App : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(androidCoreModule(this@App))
        import(viewModelModule)
        import(dataSourceModule)
        import(networkModule)
        import(localModule)
    }
}