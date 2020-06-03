package com.jbappz.githubbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jbappz.githubbrowser.model.GithubRepo
import com.jbappz.githubbrowser.view.GithubAdapter
import com.jbappz.githubbrowser.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val githubViewModel = GithubViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val githubAdapter = GithubAdapter()

        githubViewModel.getData().observe(this, Observer<List<GithubRepo>> {
            githubAdapter.addData(it)
        })

        githubViewModel.isLoadingData.observe(this, Observer<Boolean> {

        })

        githubViewModel.isErrorLiveData.observe(this, Observer<Boolean> {

        })

        recyclerViewGithub.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = githubAdapter
        }
    }

    fun search(v: View){
        val searchText = editTextSearch.text.toString()
        githubViewModel.getRepoData(searchText)
    }
}
