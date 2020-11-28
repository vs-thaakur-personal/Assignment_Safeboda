package com.safeboda.android.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.safeboda.android.R
import com.safeboda.android.ui.search.main.SearchProfileFragment

class SearchProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchProfileFragment.newInstance())
                .commitNow()
        }
    }
}