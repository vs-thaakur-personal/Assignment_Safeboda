package com.safeboda.android.ui.detail.main

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.safeboda.android.R
import com.safeboda.android.api.ApiServiceProvider
import com.safeboda.android.databinding.UserDetailFragmentBinding
import com.safeboda.android.repository.DataRepository
import com.safeboda.android.repository.DataSource
import com.safeboda.android.repository.RemoteDataSource
import com.safeboda.android.ui.detail.DetailState
import com.safeboda.android.ui.detail.ErrorState
import com.safeboda.android.ui.detail.LoadingState
import com.safeboda.android.ui.detail.SuccessState
import com.safeboda.android.ui.follow.FollowListActivity
import com.safeboda.android.ui.follow.main.FollowListFragment
import com.safeboda.android.util.ShapedDrawable

class UserDetailFragment : Fragment(), Observer<DetailState> {

    companion object {
        fun newInstance() = UserDetailFragment()
        const val KEY_URL = "key_url"
    }

    private lateinit var binding: UserDetailFragmentBinding
    private lateinit var detailViewModel: UserDetailViewModel
    private val apiService by lazy { ApiServiceProvider.providesApiService() }
    private val remoteRepo = RemoteDataSource(apiService)

    class ViewModelFactory(private val repository: DataSource) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
                return UserDetailViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = UserDetailFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = DataRepository(remoteRepo)
        val viewModelFactory = ViewModelFactory(repository)

        detailViewModel = ViewModelProvider(this, viewModelFactory)
            .get(UserDetailViewModel::class.java)

        detailViewModel.detailLiveData.observe(viewLifecycleOwner, this)
        val url = activity?.intent?.getStringExtra(KEY_URL)
        if (url != null) {
            detailViewModel.getUserDetail(url)
        }

        activity?.title = getString(R.string.user)
    }

    override fun onChanged(state: DetailState?) {
        when (state) {
            LoadingState -> {
                binding.userDetail.isVisible = false
                binding.progressBar.isVisible = true
            }
            ErrorState -> {
                binding.userDetail.isVisible = true
                binding.progressBar.isVisible = false
                Toast.makeText(context, getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG).show()
            }
            is SuccessState -> {
                with(binding) {
                    userDetail.isVisible = true
                    progressBar.isVisible = false
                    Glide.with(root.context)
                        .load(state.detail.avatar_url)
                        .apply(RequestOptions.circleCropTransform())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(imageIv)
                    nameTv.text = state.detail.name
                    activity?.title = state.detail.name

                    userNameTv.text = getString(R.string.bracket_s)
                        .format(state.detail.login)

                    gistInfoTv.text = getString(R.string.public_repo_s_public_gist_s)
                        .format(state.detail.public_repos, state.detail.public_gists)

                    locationTv.isVisible = !state.detail.location.isNullOrBlank()
                    locationTv.text = state.detail.location
                    locationTv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_location, 0, 0, 0)

                    val drawable = ShapedDrawable().applyShape(GradientDrawable.RECTANGLE)
                        .applyRadius(resources.getDimension(R.dimen.spacing_2x))
                        .applyStroke(6, ContextCompat.getColor(context!!, R.color.grey_900))
                        .applyBgColor(ContextCompat.getColor(context!!, R.color.grey_100))
                        .getShape()

                    followerTv.text = getString(R.string.followers_s)
                        .format(state.detail.followers ?: 0)
                    followerTv.background = drawable
                    followerTv.setOnClickListener {
                        state.detail.followers_url?.let { url ->
                            openFollowActivity(url, true)
                        }
                    }

                    followingTv.text = getString(R.string.following_s)
                        .format(state.detail.following ?: 0)
                    followingTv.background = drawable
                    followingTv.setOnClickListener {
                        state.detail.following_url?.let { url ->
                            openFollowActivity(url.substring(0, url.indexOf("{")), false)
                        }
                    }
                }
            }
            else -> TODO()
        }
    }

    private fun openFollowActivity(url: String, isFollower: Boolean) {
        val intent = Intent(context, FollowListActivity::class.java)
        intent.putExtra(FollowListFragment.IS_FOLLOWERS_KEY, isFollower)
        intent.putExtra(FollowListFragment.URL_KEY, url)
        startActivity(intent)
    }
}