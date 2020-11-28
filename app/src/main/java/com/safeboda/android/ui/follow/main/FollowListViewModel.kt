package com.safeboda.android.ui.follow.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safeboda.android.model.Item
import com.safeboda.android.repository.DataSource
import com.safeboda.android.ui.follow.ErrorState
import com.safeboda.android.ui.follow.FollowState
import com.safeboda.android.ui.follow.LoadingState
import com.safeboda.android.ui.follow.SuccessState
import kotlinx.coroutines.launch

class FollowListViewModel(private val repository: DataSource) : ViewModel() {
    private val localLiveData = MutableLiveData<FollowState>()

    val listLiveData: LiveData<FollowState> = localLiveData

    fun getFollowDetail(url: String) {
        viewModelScope.launch {
            showLoading()
            try {
                val detail = repository.getFollowInfo(url)
                showSuccess(detail)
            } catch (e: Exception) {
                e.printStackTrace()
                showError()
            }
        }
    }

    private fun showLoading() {
        localLiveData.value = LoadingState
    }

    private fun showError() {
        localLiveData.value = ErrorState
    }

    private fun showSuccess(users: List<Item>) {
        localLiveData.value = SuccessState(users)
    }
}