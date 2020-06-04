package com.jbappz.githubbrowser.api

import com.jbappz.githubbrowser.model.GithubReadMe
import com.jbappz.githubbrowser.model.GithubRepos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories")
    fun getRepos(@Query("q") searchText: String): Call<GithubRepos>

    @GET("repos/{owner}/{repo}/readme")
    fun getRepoReadMe(@Path("owner") owner: String, @Path("repo")repo: String): Call<GithubReadMe>
}