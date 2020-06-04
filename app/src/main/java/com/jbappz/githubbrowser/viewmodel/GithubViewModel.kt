package com.jbappz.githubbrowser.viewmodel

import androidx.lifecycle.*
import com.jbappz.githubbrowser.model.GithubRepo
import com.jbappz.githubbrowser.repository.Repository
import java.lang.Exception

class GithubViewModel: ViewModel() {
    var isLoadingData: MutableLiveData<Boolean> = MutableLiveData()
    var isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var _githubLiveData: LiveData<List<GithubRepo>> = MutableLiveData()
    private var _userId: MutableLiveData<String> = MutableLiveData()

    private var _gitHubReadMeUrl: LiveData<String> = MutableLiveData()
    private var _readMeValues: MutableLiveData<ReadMeValues> = MutableLiveData()
    private data class ReadMeValues(val owner: String, val repoName: String)
    private var _readMeData: LiveData<String> = MutableLiveData()

    init {
        getGithubData()
        gitGubReadmeUrl()
        gitHubReadMeData()
    }

    fun getData(): LiveData<List<GithubRepo>> = _githubLiveData

    fun search(userId: String) {
        isErrorLiveData.value = false
        isLoadingData.value = false

        if (_userId.value == userId) return

        isLoadingData.value = true
        _userId.value = userId
    }

    fun searchForReadMe(owner: String?, repoName: String?) {
        isErrorLiveData.value = false
        isLoadingData.value = false

        if(owner == null || repoName == null) return
        if(owner.isEmpty() || repoName.isEmpty()) return

        isLoadingData.value = true
        _readMeValues.value = ReadMeValues(owner, repoName)
    }

    fun getReadMeData(): LiveData<String> = _readMeData

    private fun getGithubData() {
        try {
            // Use switch map when _userId changes, get new github data
            _githubLiveData = Transformations
                .switchMap(_userId) {
                    isLoadingData.value = true
                    Repository.getRepoData(it)
                }
        }
        catch (e: Exception) {
            isErrorLiveData.value = true
        }
    }

    private fun gitGubReadmeUrl() {
        try {
            // Use switch map when readMe value changes
            _gitHubReadMeUrl = Transformations
                .switchMap(_readMeValues) {
                    isLoadingData.value = true
                    Repository.getReadMeUrl(it.owner, it.repoName)
                }
        }
        catch (e: Exception) {
            isErrorLiveData.value = true
        }
    }

    private fun gitHubReadMeData() {
        try {
            // Use switch map when readMe data value changes
            _readMeData = Transformations
                .switchMap(_gitHubReadMeUrl) {
                    isLoadingData.value = true
                    Repository.downloadReadMe(it)
                }
        }
        catch (e: Exception) {
            isLoadingData.value = false
        }
    }
}