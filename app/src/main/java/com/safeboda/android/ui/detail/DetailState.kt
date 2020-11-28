package com.safeboda.android.ui.detail

import com.safeboda.android.model.UserDetail

sealed class DetailState
object LoadingState : DetailState()
object ErrorState : DetailState()
data class SuccessState(val detail: UserDetail) : DetailState()