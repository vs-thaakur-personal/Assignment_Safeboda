package com.safeboda.android.ui.search.main

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.safeboda.android.R
import com.safeboda.android.api.ApiServiceProvider
import com.safeboda.android.base.ItemClickListener
import com.safeboda.android.databinding.FragmentSearchProfileBinding
import com.safeboda.android.model.Item
import com.safeboda.android.repository.DataRepository
import com.safeboda.android.repository.DataSource
import com.safeboda.android.repository.RemoteDataSource
import com.safeboda.android.ui.detail.UserDetailActivity
import com.safeboda.android.ui.detail.main.UserDetailFragment
import com.safeboda.android.ui.search.ErrorState
import com.safeboda.android.ui.search.LoadingState
import com.safeboda.android.ui.search.SearchState
import com.safeboda.android.ui.search.SuccessState
import com.safeboda.android.util.ItemMarginDecoration
import com.safeboda.android.util.ShapedDrawable


class SearchProfileFragment : Fragment(), Observer<SearchState>, TextView.OnEditorActionListener, ItemClickListener<Item> {

    companion object {
        fun newInstance() = SearchProfileFragment()
    }

    private lateinit var binding: FragmentSearchProfileBinding
    private lateinit var searchViewModel: SearchViewModel
    private val apiService by lazy { ApiServiceProvider.providesApiService() }
    private val remoteRepo = RemoteDataSource(apiService)
    private val adapter: SearchAdapter by lazy { SearchAdapter() }

    class ViewModelFactory(private val repository: DataSource) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                return SearchViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSearchProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val repository = DataRepository(remoteRepo)
        val viewModelFactory = ViewModelFactory(repository)

        searchViewModel = ViewModelProvider(this, viewModelFactory)
            .get(SearchViewModel::class.java)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.searchLiveData.observe(viewLifecycleOwner, this)

        val drawable = ShapedDrawable().applyShape(GradientDrawable.RECTANGLE)
            .applyRadius(resources.getDimension(R.dimen.spacing_2x))
            .applyStroke(6, ContextCompat.getColor(context!!, R.color.grey_900))
            .applyBgColor(ContextCompat.getColor(context!!, R.color.grey_100))
            .getShape()
        binding.searchEditText.background = drawable
        binding.searchEditText.setOnEditorActionListener(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(ItemMarginDecoration(bottomDimId = R.dimen.spacing_x))
        adapter.itemClickListener = this
    }

    override fun onChanged(state: SearchState?) {
        when (state) {
            LoadingState -> {
                binding.progressBar.isVisible = true
                binding.recyclerView.isVisible = false
            }
            ErrorState -> {
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true
                Toast.makeText(context, getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG).show()
            }
            is SuccessState -> {
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true
                adapter.setItems(state.data)
            }
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchViewModel.searchUser(v?.text.toString())
        }
        return false
    }

    override fun onItemClick(data: Item, position: Int) {
        val intent = Intent(context, UserDetailActivity::class.java)
        intent.putExtra(UserDetailFragment.KEY_URL, data.url)
        startActivity(intent)
    }
}