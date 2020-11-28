package com.safeboda.android.ui.follow

import com.safeboda.android.model.Item


sealed class FollowState
object LoadingState : FollowState()
object ErrorState : FollowState()
data class SuccessState(val users: List<Item>) : FollowState()