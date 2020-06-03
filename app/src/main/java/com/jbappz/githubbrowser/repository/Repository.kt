package com.jbappz.githubbrowser.repository

import android.accounts.NetworkErrorException
import androidx.lifecycle.MutableLiveData
import com.jbappz.githubbrowser.api.GithubRetrofitBuilder
import com.jbappz.githubbrowser.model.GithubRepo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {
    fun getRepoData(userId: String): MutableLiveData<List<GithubRepo>> {
        return object: MutableLiveData<List<GithubRepo>>() {
            override fun onActive() {
                super.onActive()
                val call: Call<List<GithubRepo>> = GithubRetrofitBuilder.API_SERVICE.getRepos(userId)
                call.enqueue(object: Callback<List<GithubRepo>> {
                    override fun onResponse(call: Call<List<GithubRepo>>, response: Response<List<GithubRepo>>) {
                        value = response.body() ?: emptyList()
                    }

                    override fun onFailure(call: Call<List<GithubRepo>>, t: Throwable) {
                        throw NetworkErrorException("Failure getting api data")
                    }
                })
            }
        }
    }
}