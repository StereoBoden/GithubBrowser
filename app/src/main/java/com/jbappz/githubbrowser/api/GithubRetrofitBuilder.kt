package com.jbappz.githubbrowser.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubRetrofitBuilder {
    private const val BASE_URL: String = "https://api.github.com/"

    val API_SERVICE: GithubApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
}