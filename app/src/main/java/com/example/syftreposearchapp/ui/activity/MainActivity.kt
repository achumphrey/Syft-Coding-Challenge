package com.example.syftreposearchapp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syftreposearchapp.R
import com.example.syftreposearchapp.di.DaggerSearchRepoComponent
import com.example.syftreposearchapp.di.NetworkModule
import com.example.syftreposearchapp.di.RepositoryModule
import com.example.syftreposearchapp.ui.MyTextWatcher
import com.example.syftreposearchapp.ui.adapter.RepoAdapter
import com.example.syftreposearchapp.utils.toTimeDuration
import com.example.syftreposearchapp.viewmodel.MainViewModel
import com.example.syftreposearchapp.viewmodel.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var repoAdapter: RepoAdapter

    var language: String = ""

    val RC_FILTER = 1001

    var apiStartTime: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repoAdapter = RepoAdapter(mutableListOf())
        rvRepos.layoutManager = LinearLayoutManager(this)
        rvRepos.adapter = repoAdapter


        DaggerSearchRepoComponent.builder()
            .networkModule(NetworkModule())
            .repositoryModule(RepositoryModule())
            .build()
            .inject(this)

        viewModel = ViewModelProviders.of(this, mainViewModelFactory).get(MainViewModel::class.java)

        viewModel.repos.observe(this, Observer {
            repoAdapter.setItems(it)
        })

        viewModel.totalCount.observe(this, Observer {

            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - apiStartTime
            tvDisplay.text = getString(R.string.total_repo_count, it, elapsedTime.toTimeDuration())
        })

        viewModel.errorMessage.observe(this, Observer {
            tvErrorMessage.text = it
        })

        viewModel.loadingState.observe(this, Observer {
            when (it) {
                MainViewModel.LoadingState.LOADING -> displayProgressbar()
                MainViewModel.LoadingState.SUCCESS -> displayRepositories()
                MainViewModel.LoadingState.ERROR -> displayErrorMessage()
                else -> displayErrorMessage()
            }
        })
        if (viewModel.lastFetchedTime == null) {
            fetchRepos()
        }
        btnRetry.setOnClickListener {
            fetchRepos()
        }

        etSearch.addTextChangedListener(object : MyTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                fetchRepos(s.toString(), language)
            }
        })

        tvFilter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            intent.putExtra("selected_language", language)
            startActivityForResult(intent, RC_FILTER)
        }
    }

    private fun displayProgressbar() {
        progressbar.visibility = View.VISIBLE
        rvRepos.visibility = View.GONE
        tvErrorMessage.visibility = View.GONE
        btnRetry.visibility = View.GONE
        tvDisplay.visibility = View.GONE
    }

    private fun displayErrorMessage() {
        progressbar.visibility = View.GONE
        rvRepos.visibility = View.GONE
        tvErrorMessage.visibility = View.VISIBLE
        btnRetry.visibility = View.VISIBLE
        tvDisplay.visibility = View.GONE
    }

    private fun displayRepositories() {
        rvRepos.visibility = View.VISIBLE
        progressbar.visibility = View.GONE
        tvErrorMessage.visibility = View.GONE
        btnRetry.visibility = View.GONE
        tvDisplay.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RC_FILTER -> {
                if (resultCode == Activity.RESULT_OK) {
                    language = data?.getStringExtra("selected_language") ?: ""
                    if (etSearch.text.toString() == "") {
                        fetchRepos(language = language)
                    } else {
                        fetchRepos(etSearch.text.toString(), language)
                    }
                }
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    fun fetchRepos(query: String = "org:github", language: String = ""){
        apiStartTime = System.currentTimeMillis()
        viewModel.fetchRepos(query, language)
    }
}
