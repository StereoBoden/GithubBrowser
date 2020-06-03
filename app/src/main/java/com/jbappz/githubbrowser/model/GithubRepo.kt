package com.jbappz.githubbrowser.model

import com.google.gson.annotations.SerializedName

data class GithubRepo(
    val name: String,
    val language: String,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    val forks: Int,
    val watchers: String,
    val owner: Owner?,
    val license: License?
)

data class Owner(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)

data class License(
    val key: String,
    val name: String,
    val url: String
)