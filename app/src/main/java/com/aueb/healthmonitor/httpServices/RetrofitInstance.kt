package com.aueb.healthmonitor.httpServices

import com.aueb.healthmonitor.staticVariables.StaticVariables
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
        fun getRetroInstance(): Retrofit {
            val gson = GsonBuilder().setLenient().create()
            return Retrofit.Builder().baseUrl(StaticVariables.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}