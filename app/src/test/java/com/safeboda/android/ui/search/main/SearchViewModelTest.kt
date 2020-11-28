package com.safeboda.android.ui.search.main

import com.safeboda.android.BaseViewModelTest
import com.safeboda.android.repository.FakeRepository
import com.safeboda.android.ui.search.SuccessState
import io.mockk.spyk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchViewModelTest : BaseViewModelTest() {

    private val repository = FakeRepository()
    lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        viewModel = spyk(SearchViewModel(repository), recordPrivateCalls = true)
    }

    @Test
    fun `testSearchUser Success VerifyOrder`() {
        viewModel.searchUser("query")
        val data = repository.getSearchResponseData()
        verifyOrder {
            viewModel["showLoading"]()
            viewModel["showSuccess"](data?.items)
        }
    }

    @Test
    fun `testSearchUser Error VerifyOrder`() {
        viewModel.searchUser("ERROR_CASE")
        verifyOrder {
            viewModel["showLoading"]()
            viewModel["showError"]()
        }
    }

    @Test
    fun testSearchUserSuccess() {
        viewModel.searchUser("fake url")
        verify { viewModel["showLoading"]() }

        val testData = repository.getSearchResponseData()?.items
        val detailState = viewModel.searchLiveData.value
        Assert.assertEquals((detailState as SuccessState).data, testData)
    }
}