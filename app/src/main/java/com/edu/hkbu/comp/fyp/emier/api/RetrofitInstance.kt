package com.edu.hkbu.comp.fyp.emier.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://emier-backend-caacbzexavbfa7gn.eastus-01.azurewebsites.net/"

    val apiService: GarminApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GarminApiService::class.java)
    }
}