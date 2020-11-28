package com.safeboda.android.ui.detail.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safeboda.android.model.UserDetail
import com.safeboda.android.repository.DataSource
import com.safeboda.android.ui.detail.DetailState
import com.safeboda.android.ui.detail.ErrorState
import com.safeboda.android.ui.detail.LoadingState
import com.safeboda.android.ui.detail.SuccessState
import kotlinx.coroutines.launch

class UserDetailViewModel(private val repository: DataSource) : ViewModel() {
    private val localLiveData = MutableLiveData<DetailState>()

    val detailLiveData: LiveData<DetailState> = localLiveData

    fun getUserDetail(url: String) {
        viewModelScope.launch {
            showLoading()
            try {
                val detail = repository.getUserDetail(url)
                showSuccess(detail)
            } catch (e: Exception) {
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

    private fun showSuccess(userDetail: UserDetail) {
        localLiveData.value = SuccessState(userDetail)
    }
}