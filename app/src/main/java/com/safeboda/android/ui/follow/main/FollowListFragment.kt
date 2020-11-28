package com.safeboda.android.ui.follow.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.safeboda.android.R
import com.safeboda.android.api.ApiServiceProvider
import com.safeboda.android.databinding.FollowListFragmentBinding
import com.safeboda.android.repository.DataRepository
import com.safeboda.android.repository.DataSource
import com.safeboda.android.repository.RemoteDataSource
import com.safeboda.android.ui.follow.ErrorState
import com.safeboda.android.ui.follow.FollowState
import com.safeboda.android.ui.follow.LoadingState
import com.safeboda.android.ui.follow.SuccessState
import com.safeboda.android.util.ItemMarginDecoration

class FollowListFragment : Fragment(), Observer<FollowState> {

    companion object {
        fun newInstance() = FollowListFragment()

        const val IS_FOLLOWERS_KEY = "key_is_follower"
        const val URL_KEY = "key_url"
    }

    private lateinit var binding: FollowListFragmentBinding
    private lateinit var followViewModel: FollowListViewModel
    private val apiService by lazy { ApiServiceProvider.providesApiService() }
    private val remoteRepo = RemoteDataSource(apiService)
    private val adapter: FollowAdapter by lazy { FollowAdapter() }

    class ViewModelFactory(private val repository: DataSource) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FollowListViewModel::class.java)) {
                return FollowListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FollowListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = DataRepository(remoteRepo)
        val viewModelFactory = ViewModelFactory(repository)

        followViewModel = ViewModelProvider(this, viewModelFactory)
            .get(FollowListViewModel::class.java)
        followViewModel.listLiveData.observe(viewLifecycleOwner, this)

        val isFollower = activity?.intent?.getBooleanExtra(IS_FOLLOWERS_KEY, false) ?: false
        if (isFollower) {
            activity?.title = getString(R.string.followers)
        } else {
            activity?.title = getString(R.string.followings)
        }

        val url = activity?.intent?.getStringExtra(URL_KEY)
        if (url != null) {
            followViewModel.getFollowDetail(url)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(ItemMarginDecoration(bottomDimId = R.dimen.spacing_x))
    }

    override fun onChanged(state: FollowState?) {
        when (state) {
            LoadingState -> {
                binding.progressBar.isVisible = true
                binding.recyclerView.isVisible = false
            }
            ErrorState -> {
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true
                Toast.makeText(context, getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG).show()
            }
            is SuccessState -> {
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true
                adapter.setItems(state.users)
            }
        }
    }
}