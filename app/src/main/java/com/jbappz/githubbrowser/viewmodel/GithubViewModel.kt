package com.jbappz.githubbrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jbappz.githubbrowser.model.GithubRepo
import com.jbappz.githubbrowser.repository.Repository
import java.lang.Exception

class GithubViewModel: ViewModel() {
    var isLoadingData: MutableLiveData<Boolean> = MutableLiveData()
    var isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var githubLiveData: LiveData<List<GithubRepo>> = MutableLiveData()
    private var _userId: MutableLiveData<String> = MutableLiveData()

    init {
        getGithubData()
    }

    fun getData(): LiveData<List<GithubRepo>> =
        githubLiveData

    fun search(userId: String) {
        isErrorLiveData.value = false
        isLoadingData.value = false

        if (_userId.value == userId) {
            return
        }

        isLoadingData.value = true
        _userId.value = userId
    }

    private fun getGithubData() {
        try {
            // Use switch map when _userId changes, get new github data
            githubLiveData = Transformations
                .switchMap(_userId) {
                    isLoadingData.value = true
                    Repository.getRepoData(it)
                }
        }
        catch (e: Exception) {
            isErrorLiveData.value = true
        }
    }
}