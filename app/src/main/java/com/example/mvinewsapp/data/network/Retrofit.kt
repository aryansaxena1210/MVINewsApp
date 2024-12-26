package com.example.mvinewsapp.data.network

import com.example.mvinewsapp.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object that provides a Retrofit instance for network operations.
 */
object Retrofit {

    /**
     * Lazy-initialized instance of [NYTimesApi] for making network requests.
     */
    val api: NYTimesApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NYTimesApi::class.java)
    }

}


