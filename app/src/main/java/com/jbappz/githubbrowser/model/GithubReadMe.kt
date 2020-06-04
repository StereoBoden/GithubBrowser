package com.jbappz.githubbrowser.model

import com.google.gson.annotations.SerializedName

data class GithubReadMe(
    @SerializedName("download_url")
    val downloadUrl: String
)