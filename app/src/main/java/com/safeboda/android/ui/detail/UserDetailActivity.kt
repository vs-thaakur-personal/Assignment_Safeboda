package com.safeboda.android.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.safeboda.android.R
import com.safeboda.android.base.BaseBackEnabledActivity
import com.safeboda.android.ui.detail.main.UserDetailFragment

class UserDetailActivity : BaseBackEnabledActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_detail_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserDetailFragment.newInstance())
                .commitNow()
        }
    }
}