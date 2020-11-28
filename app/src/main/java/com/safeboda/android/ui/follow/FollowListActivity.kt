package com.safeboda.android.ui.follow

import android.os.Bundle
import com.safeboda.android.R
import com.safeboda.android.base.BaseBackEnabledActivity
import com.safeboda.android.ui.follow.main.FollowListFragment

class FollowListActivity : BaseBackEnabledActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.follow_list_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FollowListFragment.newInstance())
                .commitNow()
        }
    }
}