package com.jbappz.githubbrowser.view.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.jbappz.githubbrowser.R
import kotlinx.android.synthetic.main.dialog_fragment_readme.*

class ReadMeDialogFragment: DialogFragment() {
    companion object {
        const val TAG: String = "ReadMeDialogFragment"
        private const val KEY_README_URL: String = "readmeURL"

        fun newInstance(readMeText: String): ReadMeDialogFragment {
            val fragment = ReadMeDialogFragment()
            val args = Bundle()
            args.putString(KEY_README_URL, readMeText)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        View.inflate(context, R.layout.dialog_fragment_readme, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val readMeData = arguments?.getString(KEY_README_URL) ?: ""
        textViewDialogFragmentReadMe.text = readMeData

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
}