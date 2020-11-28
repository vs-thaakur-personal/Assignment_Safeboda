package com.safeboda.android.ui.detail.main

import com.safeboda.android.BaseViewModelTest
import com.safeboda.android.repository.FakeRepository
import com.safeboda.android.ui.detail.SuccessState
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserDetailViewModelTest : BaseViewModelTest() {

    private val repository = FakeRepository()
    lateinit var viewModel: UserDetailViewModel

    @Before
    fun setUp() {
        viewModel = spyk(UserDetailViewModel(repository), recordPrivateCalls = true)
    }

    @Test
    fun `testGetUserDetail Success VerifyOrder`() {
        viewModel.getUserDetail("fake url")
        val data = repository.getUserDetailData()
        verifyOrder {
            viewModel["showLoading"]()
            viewModel["showSuccess"](data)
        }
    }

    @Test
    fun `testGetUserDetail Error VerifyOrder`() {
        viewModel.getUserDetail("ERROR_CASE")
        verifyOrder {
            viewModel["showLoading"]()
            viewModel["showError"]()
        }
    }

    @Test
    fun testGetUserDetailEmpty() {
        viewModel.getUserDetail("fake url")
        verify { viewModel["showLoading"]() }

        val testData = repository.getUserDetailData()
        val detailState = viewModel.detailLiveData.value
        Assert.assertEquals((detailState as SuccessState).detail, testData)
    }
}