package com.rechit.githubuserapp.api

import com.rechit.githubuserapp.model.ListUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getListUsers(): Call<ListUsersResponse>
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>
}