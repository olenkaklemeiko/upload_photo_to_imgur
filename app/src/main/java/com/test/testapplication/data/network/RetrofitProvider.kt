package com.test.testapplication.data.network

import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface RetrofitProvider {

    companion object {
        fun retrofitInstance(): Retrofit {
            val client = OkHttpClient.Builder()
            client.connectTimeout(15, TimeUnit.SECONDS)
            client.readTimeout(15, TimeUnit.SECONDS)
            client.writeTimeout(15, TimeUnit.SECONDS)

            return Retrofit.Builder()
                .baseUrl("https://api.imgur.com")
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        }

    }
}