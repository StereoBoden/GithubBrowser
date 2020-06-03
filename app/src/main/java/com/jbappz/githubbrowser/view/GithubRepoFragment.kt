package com.jbappz.githubbrowser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jbappz.githubbrowser.R
import com.jbappz.githubbrowser.model.GithubRepo
import kotlinx.android.synthetic.main.fragment_repo.*

class GithubRepoFragment: Fragment() {

    companion object {
        private const val KEY_NAME: String = "name"
        private const val KEY_LANGUAGE: String = "language"
        private const val KEY_OPEN_ISSUES_COUNT: String = "openIssuesCount"
        private const val KEY_FORKS: String = "forks"
        private const val KEY_WATCHERS: String = "watchers"

        private const val KEY_OWNER_LOGIN: String = "owner_login"
        private const val KEY_OWNER_AVATAR: String = "owner_avatar"

        private const val KEY_LICENSE_KEY: String = "license_key"
        private const val KEY_LICENSE_NAME: String = "license_name"
        private const val KEY_LICENSE_URL: String = "license_url"

        fun newInstance(githubRepo: GithubRepo): GithubRepoFragment {
            val bundle = Bundle()
            bundle.putString(KEY_NAME, githubRepo.name)
            bundle.putString(KEY_LANGUAGE, githubRepo.language)
            bundle.putInt(KEY_OPEN_ISSUES_COUNT, githubRepo.openIssuesCount)
            bundle.putInt(KEY_FORKS, githubRepo.forks)
            bundle.putString(KEY_WATCHERS, githubRepo.watchers)
            bundle.putString(KEY_OWNER_LOGIN, githubRepo.owner?.login)
            bundle.putString(KEY_OWNER_AVATAR, githubRepo.owner?.avatarUrl)
            bundle.putString(KEY_LICENSE_KEY, githubRepo.license?.key)
            bundle.putString(KEY_LICENSE_NAME, githubRepo.license?.name)
            bundle.putString(KEY_LICENSE_URL, githubRepo.license?.url)

            return GithubRepoFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_repo, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textViewDialogName.append(format(arguments?.getString(KEY_NAME)))
        textViewDialogLanguage.append(format(arguments?.getString(KEY_LANGUAGE)))
        textViewDialogOpenIssues.append(format(arguments?.getInt(KEY_OPEN_ISSUES_COUNT).toString()))
        textViewDialogForks.append(format(arguments?.getInt(KEY_FORKS).toString()))
        textViewDialogWatchers.append(format(arguments?.getString(KEY_WATCHERS)))
        textViewDialogOwnerLogin.append(format(arguments?.getString(KEY_OWNER_LOGIN)))
        textViewDialogLicenseKey.append(format(arguments?.getString(KEY_LICENSE_KEY)))
        textViewDialogLicenseName.append(format(arguments?.getString(KEY_LICENSE_NAME)))
        textViewDialogLicenseUrl.append(format(arguments?.getString(KEY_LICENSE_URL)))
    }

    private fun format(input: String?): String {
        return input ?: "Not available"
    }
}