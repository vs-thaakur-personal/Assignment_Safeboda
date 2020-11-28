package com.safeboda.android

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

open class BaseViewModelTest {
    // Using this rule for tests that use Architecture Components.
    @get:Rule
    var rule = InstantTaskExecutorRule()

    // Using this rule to host test containing coroutines
    @ExperimentalCoroutinesApi
    @get:Rule
    val coRoutineDispatcherRule = CoroutineDispatcherRule()
}