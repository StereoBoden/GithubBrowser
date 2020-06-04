package com.jbappz.githubbrowser.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.jbappz.githubbrowser.R
import com.jbappz.githubbrowser.model.GithubRepo
import com.jbappz.githubbrowser.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.fragment_repo.*

class GithubRepoFragment: Fragment() {

    companion object {
        private const val KEY_NAME: String = "name"

        private const val KEY_OWNER_LOGIN: String = "owner_login"
        private const val KEY_OWNER_AVATAR: String = "owner_avatar"

        private const val KEY_LANGUAGE: String = "language"
        private const val KEY_DESCRIPTION: String = "description"
        private const val KEY_OPEN_ISSUES_COUNT: String = "openIssuesCount"
        private const val KEY_FORKS: String = "forks"
        private const val KEY_WATCHERS: String = "watchers"
        private const val KEY_STARGAZERS_COUNT: String = "stargazersCount"
        private const val KEY_DEFAULT_BRANCH: String = "defaultBranch"

        private const val KEY_LICENSE_NAME: String = "license_name"
        private const val KEY_LICENSE_KEY: String = "license_key"
        private const val KEY_LICENSE_URL: String = "license_url"

        fun newInstance(githubRepo: GithubRepo): GithubRepoFragment {
            val bundle = Bundle()
            bundle.putString(KEY_NAME, githubRepo.name)
            bundle.putString(KEY_OWNER_LOGIN, githubRepo.owner?.login)
            bundle.putString(KEY_OWNER_AVATAR, githubRepo.owner?.avatarUrl)
            bundle.putString(KEY_LANGUAGE, githubRepo.language)
            bundle.putString(KEY_DESCRIPTION, githubRepo.description)
            bundle.putInt(KEY_OPEN_ISSUES_COUNT, githubRepo.openIssuesCount)
            bundle.putInt(KEY_FORKS, githubRepo.forks)
            bundle.putString(KEY_WATCHERS, githubRepo.watchers)
            bundle.putInt(KEY_STARGAZERS_COUNT, githubRepo.stargazersCount)
            bundle.putString(KEY_DEFAULT_BRANCH, githubRepo.defaultBranch)
            bundle.putString(KEY_LICENSE_NAME, githubRepo.license?.name)
            bundle.putString(KEY_LICENSE_KEY, githubRepo.license?.key)
            bundle.putString(KEY_LICENSE_URL, githubRepo.license?.url)

            return GithubRepoFragment().apply {
                arguments = bundle
            }
        }
    }

    private val githubViewModel = GithubViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_repo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repoName = arguments?.getString(KEY_NAME)
        val ownerLogin = arguments?.getString(KEY_OWNER_LOGIN)

        super.onViewCreated(view, savedInstanceState)
        textViewFragmentName.append(format(repoName))
        textViewFragmentOwnerLogin.append(format(ownerLogin))
        textViewFragmentLanguage.append(format(arguments?.getString(KEY_LANGUAGE)))
        textViewFragmentDescription.append(format(arguments?.getString(KEY_DESCRIPTION)))
        textViewFragmentOpenIssues.append(format(arguments?.getInt(KEY_OPEN_ISSUES_COUNT).toString()))
        textViewFragmentForks.append(format(arguments?.getInt(KEY_FORKS).toString()))
        textViewFragmentWatchers.append(format(arguments?.getString(KEY_WATCHERS)))
        textViewFragmentStargazersCount.append(format(arguments?.getInt(KEY_STARGAZERS_COUNT).toString()))
        textViewFragmentDefaultBranch.append(format(arguments?.getString(KEY_DEFAULT_BRANCH)))
        textViewFragmentLicenseName.append(format(arguments?.getString(KEY_LICENSE_NAME)))
        textViewFragmentLicenseKey.append(format(arguments?.getString(KEY_LICENSE_KEY)))
        textViewFragmentLicenseUrl.append(format(arguments?.getString(KEY_LICENSE_URL)))

        buttonFragmentViewReadMe.setOnClickListener {
            githubViewModel.searchForReadMe(ownerLogin, repoName)
        }

        buttonFragmentOK.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        githubViewModel.getReadMeData().observe(viewLifecycleOwner, Observer {
            progressBarReadMe.visibility = View.GONE
            if(it.isEmpty()) {
                Toast.makeText(context, R.string.error_no_readme, Toast.LENGTH_LONG).show()
            }
            else {
                ReadMeDialogFragment.newInstance(it)
                    .show(parentFragmentManager, ReadMeDialogFragment.TAG)
            }
        })

        githubViewModel.isLoadingData.observe(viewLifecycleOwner, Observer {
            progressBarReadMe.visibility = if(it) View.VISIBLE else View.GONE
        })
    }

    private fun format(input: String?): String {
        return input ?: "Not available"
    }
}