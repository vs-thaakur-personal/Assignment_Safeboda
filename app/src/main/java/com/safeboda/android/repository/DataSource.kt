package com.safeboda.android.repository

import com.safeboda.android.model.Item
import com.safeboda.android.model.SearchResponse
import com.safeboda.android.model.UserDetail

interface DataSource {
    suspend fun searchUser(user: String): SearchResponse
    suspend fun getUserDetail(url: String): UserDetail
    suspend fun getFollowInfo(url: String): List<Item>
}