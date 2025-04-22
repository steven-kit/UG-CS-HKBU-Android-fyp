package com.edu.hkbu.comp.fyp.emier.api

import retrofit2.http.GET
import retrofit2.http.POST

interface GarminApiService {
    @GET("user/deregister")
    suspend fun deregisterUser(@retrofit2.http.Query("userAccessToken") userAccessToken: String): retrofit2.Response<Unit>

    @GET("backfill/stress")
    suspend fun getStressData(
        @retrofit2.http.Query("uat") uat: String,
        @retrofit2.http.Query("startTime") startTime: String,
        @retrofit2.http.Query("endTime") endTime: String
    ): retrofit2.Response<Unit>

    @POST("/user/registerDevice")
    suspend fun registerToken(
        @retrofit2.http.Query("uat") uat: String,
        @retrofit2.http.Query("deviceId") deviceId: String
    ): retrofit2.Response<Unit>
}