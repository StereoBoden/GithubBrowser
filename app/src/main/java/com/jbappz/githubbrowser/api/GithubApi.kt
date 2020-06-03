package com.jbappz.githubbrowser.api

import com.jbappz.githubbrowser.model.GithubRepo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface GithubApi {
    @GET("users/{userId}/repos")
    fun getRepos(@Path("userId") userId: String): Call<List<GithubRepo>>
}