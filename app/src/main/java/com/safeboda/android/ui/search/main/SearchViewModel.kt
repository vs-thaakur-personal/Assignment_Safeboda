package com.safeboda.android.ui.search.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safeboda.android.api.ApiService
import com.safeboda.android.model.Item
import com.safeboda.android.repository.DataSource
import com.safeboda.android.ui.search.ErrorState
import com.safeboda.android.ui.search.LoadingState
import com.safeboda.android.ui.search.SearchState
import com.safeboda.android.ui.search.SuccessState
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: DataSource) : ViewModel() {
    private val localLiveData = MutableLiveData<SearchState>()

    val searchLiveData: LiveData<SearchState> = localLiveData

    fun searchUser(query: String) {
        viewModelScope.launch {
            showLoading()
            try {
                val response = repository.searchUser(query)
                showSuccess(response.items)
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

    private fun showSuccess(items: List<Item>) {
        localLiveData.value = SuccessState(items)
    }
}