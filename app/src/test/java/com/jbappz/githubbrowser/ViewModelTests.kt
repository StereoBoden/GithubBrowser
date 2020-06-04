package com.jbappz.githubbrowser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jbappz.githubbrowser.model.GithubRepo
import com.jbappz.githubbrowser.viewmodel.GithubViewModel
import junit.framework.Assert.assertEquals

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ViewModelTests {

    //TODO: Add full unit test coverage

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: GithubViewModel

    @Mock
    private lateinit var isLoadingDataObserver: Observer<Boolean>

    @Mock
    private lateinit var repoObserver: Observer<List<GithubRepo>>

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        viewModel = GithubViewModel()
        viewModel.isLoadingData.observeForever(isLoadingDataObserver)
        viewModel.getData().observeForever(repoObserver)
    }

    @Test
    fun `Given user searches for a Repo the loading indicator shows`() {
        viewModel.search("SomeGithubRepo")
        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(isLoadingDataObserver, times(2)).onChanged(capture())
            assertEquals(true, value)
        }
    }

    @Test
    fun `Given user searches the same repo twice the data is not loaded twice`() {
        viewModel.search("SomeGithubRepo")
        viewModel.search("SomeGithubRepo")

        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(isLoadingDataObserver, times(3)).onChanged(capture())
            assertEquals(false, value)
        }
    }

    @Test
    fun `Given a repo has been round and the ReadMe is searched for`() {
        viewModel.searchForReadMe("Owner", "Repo Name")

        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(isLoadingDataObserver, times(2)).onChanged(capture())
            assertEquals(true, value)
        }
    }

    @Test
    fun `Given the repo is found the data for the ReadMe is not loaded twice`() {
        viewModel.searchForReadMe("Owner", "Repo Name")
        viewModel.searchForReadMe("Owner", "Repo Name")

        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(isLoadingDataObserver, times(3)).onChanged(capture())
            assertEquals(false, value)
        }
    }

    @Test
    fun `Given null or empty owner data from the repo search, the ReadMe is not searched for`() {
        viewModel.searchForReadMe(null, "Repo Name")
        viewModel.searchForReadMe("", "Repo Name")

        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(isLoadingDataObserver, times(2)).onChanged(capture())
            assertEquals(false, value)
        }
    }

    @Test
    fun `Given null or empty repo name data from the repo search, the ReadMe is not searched for`() {
        viewModel.searchForReadMe("Owner", null)
        viewModel.searchForReadMe("Owner", "")

        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(isLoadingDataObserver, times(2)).onChanged(capture())
            assertEquals(false, value)
        }
    }

    @Test
    fun `Given there are valid owner and repo name values, the data is not searched for twice`() {
        viewModel.searchForReadMe("Owner", "Repo Name")
        viewModel.searchForReadMe("Owner", "Repo Name")
        val captor = ArgumentCaptor.forClass(Boolean::class.java)
        captor.run {
            verify(isLoadingDataObserver, times(3)).onChanged(capture())
            assertEquals(false, value)
        }
    }
}
