package com.example.syftreposearchapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.syftreposearchapp.data.model.GitRepoModel
import com.example.syftreposearchapp.data.model.Item
import com.example.syftreposearchapp.data.repository.RepositoryImpl
import com.example.syftreposearchapp.ui.activity.MainActivity
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.reactivex.Single
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner
import java.net.UnknownHostException

@RunWith(BlockJUnit4ClassRunner::class)
class MainViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var repository: RepositoryImpl
    lateinit var mainViewModel: MainViewModel

    private var totalCount : Int = 123
    private var items = mutableListOf<Item>()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mainViewModel = MainViewModel(repository)
        items.add(Item("test"))
    }

    @Test
    fun fetchRepos_with_success() {
        var repos = GitRepoModel(totalCount, items)

        val expectedRepos = items
        every {repository.fetchGitRepos("")} returns (Single.just(repos))

        mainViewModel.fetchRepos("")

        assertEquals(expectedRepos, mainViewModel.repos.value)
        assertEquals(MainViewModel.LoadingState.SUCCESS, mainViewModel.loadingState.value)
        assertEquals(null, mainViewModel.errorMessage.value)
    }

    @Test
    fun fetchRepos_without_success_Nothing_Returned() {
        var repos = GitRepoModel(totalCount, emptyList())

        every {repository.fetchGitRepos("")} returns Single.just(repos)

        mainViewModel.fetchRepos("")

        assertEquals(null, mainViewModel.repos.value)
        assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        assertEquals("No Repos Found", mainViewModel.errorMessage.value)
    }

    @Test
    fun fetchRepos_with_NetworkError() {
        every {repository.fetchGitRepos("")} returns (Single.error(UnknownHostException("Something Wrong")))

        mainViewModel.fetchRepos("")

        assertEquals(null, mainViewModel.repos.value)
        assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        assertEquals("No Network", mainViewModel.errorMessage.value)
    }

    @Test
    fun fetchRepos_with_otherError() {
        every {repository.fetchGitRepos("")} returns (Single.error(RuntimeException("ABC")))

        mainViewModel.fetchRepos("")

        assertEquals(null, mainViewModel.repos.value)
        assertEquals(MainViewModel.LoadingState.ERROR, mainViewModel.loadingState.value)
        assertEquals("ABC", mainViewModel.errorMessage.value)
    }

    @Test
    fun getActivityTest(){
        val activityClass = mainViewModel.getActivity()
        assertTrue(activityClass == MainActivity::class.java)
    }

    @After
    fun tearDown() {
    }
}