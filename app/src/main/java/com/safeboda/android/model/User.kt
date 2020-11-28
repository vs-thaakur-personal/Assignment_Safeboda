package com.safeboda.android.model

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)

data class Item(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val score: Double,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)

data class UserDetail(
    val avatar_url: String,
    val company: String?,
    val created_at: String,
    val email: String?,
    val followers: Int?,
    val followers_url: String?,
    val following: Int?,
    val following_url: String?,
    val gists_url: String,
    val id: Int,
    val location: String?,
    val login: String,
    val name: String,
    val public_gists: Int?,
    val public_repos: Int?,
    val repos_url: String,
    val type: String,
    val updated_at: String,
    val url: String
)