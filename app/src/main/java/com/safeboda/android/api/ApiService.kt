package com.safeboda.android.api

import com.safeboda.android.model.Item
import com.safeboda.android.model.SearchResponse
import com.safeboda.android.model.UserDetail
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("/search/users")
    suspend fun searchUser(@Query("q") user: String): SearchResponse

    @GET
    suspend fun userDetail(@Url user: String): UserDetail

    @GET
    suspend fun userFollowInfo(@Url user: String): List<Item>
}