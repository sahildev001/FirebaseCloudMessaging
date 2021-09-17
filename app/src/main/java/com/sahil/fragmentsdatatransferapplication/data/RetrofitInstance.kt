package com.sahil.fragmentsdatatransferapplication.data

import com.sahil.fragmentsdatatransferapplication.utils.AppConstants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())
                .build()
        }
      val api by lazy{
            retrofit.create(NotificationApi::class.java)
        }
    }
}