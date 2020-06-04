package com.jbappz.githubbrowser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.jbappz.githubbrowser.view.fragments.GithubSearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            val gitHubSearchFragment = GithubSearchFragment.newInstance()
            replace(R.id.fragmentContainer, gitHubSearchFragment)
        }
    }
}
