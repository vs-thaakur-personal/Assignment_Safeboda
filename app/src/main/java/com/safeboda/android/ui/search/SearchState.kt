package com.safeboda.android.ui.search

import com.safeboda.android.model.Item

sealed class SearchState
object LoadingState : SearchState()
object ErrorState : SearchState()
data class SuccessState(val data: List<Item>) : SearchState()