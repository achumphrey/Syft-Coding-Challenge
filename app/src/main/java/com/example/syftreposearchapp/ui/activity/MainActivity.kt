package com.example.syftreposearchapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syftreposearchapp.R
import com.example.syftreposearchapp.data.remote.WebServices
import com.example.syftreposearchapp.data.repository.RepositoryImpl
import com.example.syftreposearchapp.ui.adapter.RepoAdapter
import com.example.syftreposearchapp.viewmodel.MainViewModel
import com.example.syftreposearchapp.viewmodel.factory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var repoAdapter: RepoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

     //   setupRecyclerView()

        viewModel = ViewModelProviders.of(this, MainViewModelFactory(RepositoryImpl(WebServices.instance)))
            .get(MainViewModel::class.java)

        viewModel.repos.observe(this, Observer {
            repoAdapter = RepoAdapter(it)
            repoAdapter.notifyDataSetChanged()

            rvRepos.layoutManager = LinearLayoutManager(this)
            rvRepos.adapter = repoAdapter
        })

       /* viewModel.errorMessage.observe(this, Observer {
            tvMessage.text = it
        })*/

        viewModel.loadingState.observe(this, Observer {
            when (it) {
                MainViewModel.LoadingState.LOADING -> displayProgressbar()
                MainViewModel.LoadingState.SUCCESS -> displayList()
                MainViewModel.LoadingState.ERROR -> displayMessageContainer()
                else -> displayMessageContainer()
            }
        })
        if (viewModel.lastFetchedTime == null) {
            viewModel.fetchrepos()
        }
    }

    private fun displayProgressbar() {
        progressbar.visibility = View.VISIBLE
    //    rvRepos.visibility = View.GONE
    //    llMessageContainer.visibility = View.GONE
    }

    private fun displayMessageContainer() {
      //  llMessageContainer.visibility = View.VISIBLE
      //  rvRepos.visibility = View.GONE
        progressbar.visibility = View.GONE
    }

    private fun displayList() {

      //  llMessageContainer.visibility = View.GONE
        rvRepos.visibility = View.VISIBLE
        progressbar.visibility = View.GONE
    }

   /* private fun setupRecyclerView() {
        rvRepos.layoutManager = LinearLayoutManager(this)
        repoAdapter = RepoAdapter()
        rvRepos.adapter = repoAdapter
    }*/
}
