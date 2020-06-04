package com.jbappz.githubbrowser.repository

import androidx.lifecycle.MutableLiveData
import com.jbappz.githubbrowser.api.GithubRetrofitBuilder
import com.jbappz.githubbrowser.model.GithubReadMe
import com.jbappz.githubbrowser.model.GithubRepo
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

object Repository {

    fun getRepoData(userId: String): MutableLiveData<List<GithubRepo>> {
        return object: MutableLiveData<List<GithubRepo>>() {
            override fun onActive() {
                super.onActive()
                val call: Call<List<GithubRepo>> = GithubRetrofitBuilder.API_SERVICE.getRepos(userId)
                call.enqueue(object: Callback<List<GithubRepo>> {
                    override fun onResponse(call: Call<List<GithubRepo>>, response: Response<List<GithubRepo>>) {
                        // Body must not be null, return empty list if null
                        value = response.body() ?: emptyList()
                    }

                    override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                        value = emptyList()
                    }
                })
            }
        }
    }

    fun getReadMeUrl(owner: String, repoName: String): MutableLiveData<String> {
        return object : MutableLiveData<String>() {
            override fun onActive() {
                super.onActive()
                val call: Call<GithubReadMe> = GithubRetrofitBuilder.API_SERVICE.getRepoReadMe(owner, repoName)
                call.enqueue(object : Callback<GithubReadMe> {
                    override fun onResponse(call: Call<GithubReadMe>, response: Response<GithubReadMe>) {
                        value = response.body()?.downloadUrl ?: ""
                    }

                    override fun onFailure(call: Call<GithubReadMe>, t: Throwable) {
                        value = ""
                    }
                })
            }
        }
    }

    fun downloadReadMe(readMeUrl: String): MutableLiveData<String> {
        return object : MutableLiveData<String>() {
            override fun onActive() {
                super.onActive()

                if(readMeUrl.isEmpty()) {
                    postValue("")
                    return
                }

                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(readMeUrl)
                    .build()

                client.run {
                    newCall(request).enqueue(object : okhttp3.Callback {
                        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                            postValue(response.body()?.string())
                        }

                        override fun onFailure(call: okhttp3.Call, e: IOException) {
                            postValue("")
                        }
                    })
                }
            }
        }
    }
}