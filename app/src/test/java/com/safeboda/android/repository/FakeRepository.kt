package com.safeboda.android.repository

import com.safeboda.android.ResourceReader
import com.safeboda.android.model.Item
import com.safeboda.android.model.SearchResponse
import com.safeboda.android.model.UserDetail
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class FakeRepository : DataSource {
    private val resourceReader by lazy {
        ResourceReader()
    }

    override suspend fun searchUser(user: String): SearchResponse {
        if (user == "ERROR_CASE") {
            throw RuntimeException()
        }
        return getSearchResponseData()!!
    }

    override suspend fun getUserDetail(url: String): UserDetail {
        if (url == "ERROR_CASE") {
            throw RuntimeException()
        }
        return getUserDetailData()!!
    }

    override suspend fun getFollowInfo(url: String): List<Item> {
        if (url == "ERROR_CASE") {
            throw RuntimeException()
        }
        return getFakeFollower()!!
    }

    fun getUserDetailData(): UserDetail? {
        val file = resourceReader.getFileAsText(this, "UserDetail.json")
        return getUserDetailAdapter().fromJson(file)
    }

    private fun getUserDetailAdapter(): JsonAdapter<UserDetail> {
        val type = Types.newParameterizedType(UserDetail::class.java)
        return Moshi.Builder().build().adapter(type)
    }


    fun getSearchResponseData(): SearchResponse? {
        val file = resourceReader.getFileAsText(this, "SearchData.json")
        return getSearchResponseAdapter().fromJson(file)
    }

    private fun getSearchResponseAdapter(): JsonAdapter<SearchResponse> {
        val type = Types.newParameterizedType(SearchResponse::class.java)
        return Moshi.Builder().build().adapter(type)
    }


     fun getFakeFollower(): List<Item> {
        val file = resourceReader.getFileAsText(this, "Followers.json")
        return getEventAdapter().fromJson(file)!!
    }

    private fun getEventAdapter(): JsonAdapter<List<Item>> {
        val type = Types.newParameterizedType(List::class.java, Item::class.java)
        return Moshi.Builder().build().adapter(type)
    }
}