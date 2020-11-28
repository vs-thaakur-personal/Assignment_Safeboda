package com.safeboda.android.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.safeboda.android.R

abstract class BaseBackEnabledActivity : AppCompatActivity() {
    private var contentFrame: FrameLayout? = null
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.activity_base)

        toolbar = findViewById(R.id.toolbar)
        contentFrame = findViewById(R.id.activityContent)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun setContentView(layoutResId: Int) {
        layoutInflater.inflate(layoutResId, contentFrame, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}