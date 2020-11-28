package com.safeboda.android.repository

import com.safeboda.android.model.Item
import com.safeboda.android.model.SearchResponse
import com.safeboda.android.model.UserDetail

class DataRepository(private val dataSource: DataSource): DataSource {
    override suspend fun searchUser(user: String): SearchResponse {
        return dataSource.searchUser(user)
    }

    override suspend fun getUserDetail(url: String): UserDetail {
        return dataSource.getUserDetail(url)
    }

    override suspend fun getFollowInfo(url: String): List<Item> {
        return dataSource.getFollowInfo(url)
    }
}