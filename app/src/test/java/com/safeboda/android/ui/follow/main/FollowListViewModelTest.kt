package com.safeboda.android.ui.follow.main

import com.safeboda.android.BaseViewModelTest
import com.safeboda.android.repository.FakeRepository
import com.safeboda.android.ui.follow.SuccessState
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FollowListViewModelTest : BaseViewModelTest() {

    private val repository = FakeRepository()
    lateinit var viewModel: FollowListViewModel

    @Before
    fun setUp() {
        viewModel = spyk(FollowListViewModel(repository), recordPrivateCalls = true)
    }

    @Test
    fun `testFollowDetail Success VerifyOrder`() {
        viewModel.getFollowDetail("query")
        val data = repository.getFakeFollower()
        verifyOrder {
            viewModel["showLoading"]()
            viewModel["showSuccess"](data)
        }
    }

    @Test
    fun `testFollowDetail Error VerifyOrder`() {
        viewModel.getFollowDetail("ERROR_CASE")
        verifyOrder {
            viewModel["showLoading"]()
            viewModel["showError"]()
        }
    }

    @Test
    fun testFollowDetailSuccess() {
        viewModel.getFollowDetail("fake url")
        verify { viewModel["showLoading"]() }

        val testData = repository.getFakeFollower()
        val detailState = viewModel.listLiveData.value
        Assert.assertEquals((detailState as SuccessState).users, testData)
    }
}