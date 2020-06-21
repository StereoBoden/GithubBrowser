package com.jbappz.githubbrowser.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jbappz.githubbrowser.R
import com.jbappz.githubbrowser.model.GithubRepo
import com.jbappz.githubbrowser.view.GithubAdapter
import com.jbappz.githubbrowser.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class GithubSearchFragment: Fragment() {

    companion object {
        fun newInstance(): GithubSearchFragment = GithubSearchFragment()
    }
    private lateinit var githubViewModel: GithubViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        githubViewModel = ViewModelProvider(requireActivity()).get(GithubViewModel::class.java)

        // Recyclerview adapter
        val githubAdapter = GithubAdapter().apply {
            itemClick = {
                showFragment(it)
            }
        }

        // Github repo data for searched user id
        githubViewModel.getData().observe(viewLifecycleOwner, Observer<List<GithubRepo>> {
            githubViewModel.isLoadingData.value = false
            githubAdapter.addData(it)
            if(it.isEmpty()) Toast.makeText(context, R.string.error_no_repos, Toast.LENGTH_LONG).show()
        })

        // Bind progress dialog visibility to isLoadingData
        githubViewModel.isLoadingData.observe(viewLifecycleOwner, Observer<Boolean> {
            progressBar.visibility = if(it) View.VISIBLE else View.GONE
        })

        // Show error textview on error
        githubViewModel.isErrorLiveData.observe(viewLifecycleOwner, Observer<Boolean> {
            textViewResultsError.visibility = if(it) View.VISIBLE else View.GONE
        })

        recyclerViewGithub.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = githubAdapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        buttonSearch.setOnClickListener {
            val searchText = editTextSearch.text.toString()
            githubViewModel.search(searchText)
        }
    }

    private fun showFragment(githubRepo: GithubRepo) {
        parentFragmentManager.commit {
            githubViewModel.selectedGithubRepo.value = githubRepo
            val githubRepoFragment = GithubRepoFragment.newInstance()
            setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slide_to_left,
                R.anim.slide_from_left,
                R.anim.slide_to_right
            )
            replace(R.id.fragmentContainer, githubRepoFragment)
            addToBackStack(null)
        }
    }
}