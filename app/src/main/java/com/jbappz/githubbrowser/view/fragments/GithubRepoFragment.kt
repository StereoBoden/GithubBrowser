package com.jbappz.githubbrowser.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jbappz.githubbrowser.R
import com.jbappz.githubbrowser.model.GithubRepo
import com.jbappz.githubbrowser.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.fragment_repo.*

class GithubRepoFragment: Fragment() {

    companion object {
        fun newInstance() = GithubRepoFragment()
    }

    private lateinit var githubViewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        githubViewModel = ViewModelProvider(requireActivity()).get(GithubViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_repo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        githubViewModel.selectedGithubRepo.observe(viewLifecycleOwner, Observer<GithubRepo> {
            updateView(it)
        })

        buttonFragmentOK.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        buttonFragmentViewReadMe.setOnClickListener {
            ReadMeDialogFragment.newInstance().show(parentFragmentManager, ReadMeDialogFragment.TAG)
        }

        githubViewModel.isLoadingData.observe(viewLifecycleOwner, Observer {
            progressBarReadMe.visibility = if(it) View.VISIBLE else View.GONE
        })
    }

    private fun updateView(repo: GithubRepo) {
        textViewFragmentName.append(format(repo.name))
        textViewFragmentOwnerLogin.append(format(repo.owner?.login))
        textViewFragmentLanguage.append(format(repo.language))
        textViewFragmentDescription.append(format(repo.description))
        textViewFragmentOpenIssues.append(format(repo.openIssuesCount.toString()))
        textViewFragmentForks.append(format(repo.forks.toString()))
        textViewFragmentWatchers.append(format(repo.watchers))
        textViewFragmentStargazersCount.append(format(repo.stargazersCount.toString()))
        textViewFragmentDefaultBranch.append(format(repo.defaultBranch))
        textViewFragmentLicenseName.append(format(repo.license?.name))
        textViewFragmentLicenseKey.append(format(repo.license?.key))
        textViewFragmentLicenseUrl.append(format(repo.license?.url))
    }

    private fun format(input: String?): String {
        return input ?: "Not available"
    }
}