package com.jbappz.githubbrowser.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jbappz.githubbrowser.R
import com.jbappz.githubbrowser.viewmodel.GithubViewModel
import kotlinx.android.synthetic.main.dialog_fragment_readme.*

class ReadMeDialogFragment: DialogFragment() {
    companion object {
        const val TAG: String = "ReadMeDialogFragment"
        fun newInstance() = ReadMeDialogFragment()
    }

    private lateinit var githubViewModel: GithubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        githubViewModel = ViewModelProvider(requireActivity()).get(GithubViewModel::class.java)

        val ownerLogin = githubViewModel.selectedGithubRepo.value?.owner?.login
        val repoName = githubViewModel.selectedGithubRepo.value?.name
        githubViewModel.searchForReadMe(ownerLogin, repoName)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        View.inflate(context, R.layout.dialog_fragment_readme, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        githubViewModel.getReadMeData().observe(viewLifecycleOwner, Observer<String> {
            githubViewModel.isLoadingData.value = false
            textViewDialogFragmentReadMe.text = it
        })

        buttonDialogFragmentOk.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroy() {
        super.onDestroy()
        githubViewModel.clearReadMeData()
    }
}