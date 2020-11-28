package com.safeboda.android.repository

import com.safeboda.android.api.ApiService
import com.safeboda.android.model.Item
import com.safeboda.android.model.SearchResponse
import com.safeboda.android.model.UserDetail

class RemoteDataSource(private val service: ApiService) : DataSource {
    override suspend fun searchUser(user: String): SearchResponse {
        return service.searchUser(user)
    }

    override suspend fun getUserDetail(url: String): UserDetail {
        return service.userDetail(url)
    }

    override suspend fun getFollowInfo(url: String): List<Item> {
        return service.userFollowInfo(url)
    }

}