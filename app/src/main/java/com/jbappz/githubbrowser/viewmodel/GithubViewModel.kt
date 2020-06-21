package com.jbappz.githubbrowser.viewmodel

import androidx.lifecycle.*
import com.jbappz.githubbrowser.model.GithubRepo
import com.jbappz.githubbrowser.repository.Repository
import java.lang.Exception

class GithubViewModel: ViewModel() {
    // Loading and error live data
    var isLoadingData: MutableLiveData<Boolean> = MutableLiveData()
    var isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    // Live github repo data
    private var _githubLiveData: LiveData<List<GithubRepo>> = MutableLiveData()
    private var _repoName: MutableLiveData<String> = MutableLiveData()

    // Selected Github Repo
    var selectedGithubRepo: MutableLiveData<GithubRepo> = MutableLiveData()

    // Github readme data
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

    // Search github by repo name
    fun search(repoName: String) {
        isErrorLiveData.value = false
        isLoadingData.value = false

        if (_repoName.value == repoName) return

        isLoadingData.value = true
        _repoName.value = repoName
    }

    // Search for repo readme
    fun searchForReadMe(owner: String?, repoName: String?) {
        isErrorLiveData.value = false
        isLoadingData.value = false

        if(owner == null || repoName == null) return
        if(owner.isEmpty() || repoName.isEmpty()) return
        if (_readMeValues.value == ReadMeValues(owner, repoName)) return

        isLoadingData.value = true
        _readMeValues.value = ReadMeValues(owner, repoName)
    }

    fun getReadMeData(): LiveData<String> = _readMeData
    fun clearReadMeData() {
        _readMeValues.postValue(ReadMeValues("", ""))
    }

    private fun getGithubData() {
        try {
            _githubLiveData = Transformations
                .switchMap(_repoName) {
                    Repository.getRepoData(it)
                }
        }
        catch (e: Exception) {
            isErrorLiveData.value = true
            isLoadingData.value = false
        }
    }

    private fun gitGubReadmeUrl() {
        try {
            // Use switch map when readMe value changes
            _gitHubReadMeUrl = Transformations
                .switchMap(_readMeValues) {
                    Repository.getReadMeUrl(it.owner, it.repoName)
                }
        }
        catch (e: Exception) {
            isErrorLiveData.value = true
            isLoadingData.value = false
        }
    }

    private fun gitHubReadMeData() {
        try {
            // Use switch map when readMe data value changes
            _readMeData = Transformations
                .switchMap(_gitHubReadMeUrl) {
                    Repository.downloadReadMe(it)
                }
        }
        catch (e: Exception) {
            isErrorLiveData.value = true
            isLoadingData.value = false
        }
    }
}