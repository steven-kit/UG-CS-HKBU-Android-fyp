package com.edu.hkbu.comp.fyp.emier.api

import retrofit2.http.GET

interface GarminApiService {
    @GET("user/deregister")
    suspend fun deregisterUser(@retrofit2.http.Query("userAccessToken") userAccessToken: String): retrofit2.Response<Unit>
}