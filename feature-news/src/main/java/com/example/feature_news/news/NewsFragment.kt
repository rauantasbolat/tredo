package com.example.feature_news.news

import android.content.Intent
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import com.example.core.ui.BaseFragment
import com.example.feature_news.R
import com.example.feature_news.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadStateAdapter
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.toArticle
import com.example.core.ui.WebViewActivity
import com.example.feature_news.adapters.NewsLoadStateAdapter
import com.example.feature_news.adapters.NewsPagingAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment<FragmentNewsBinding, NewsViewModel>(
    R.layout.fragment_news, FragmentNewsBinding::inflate
), SortFragment.SortSelectionListener {
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsPagingAdapter
    private var queryStr: String = "popular"

    companion object {
        val TAG = "NewsFragment"
    }

    override fun setView() {
        viewModel.searchNews(queryStr, "popularity")
        adapter = NewsPagingAdapter(requireActivity().menuInflater)
        binding.newsFragmentRecyclerView.adapter = adapter.withLoadStateFooter(
            footer = NewsLoadStateAdapter { adapter.retry() }
        )
        binding.sort.setOnClickListener {
            SortFragment().show(childFragmentManager, SortFragment.TAG)
        }
        binding.newsFragmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.fragmentNewsFavouriteIV.setOnClickListener {
            findNavController().navigate(R.id.action_newsFragment_to_savedNewsFragment)
        }
        viewModel.newsFlow.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    queryStr = query
                    viewModel.searchNews(queryStr)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.newsFlow.observe(viewLifecycleOwner) { pagingData ->
            lifecycleScope.launch {
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val selectedArticle = adapter.getSelectedArticle()
        selectedArticle?.let { article ->
            when (item.itemId) {
                R.id.action_open_webview -> {
                    val intent = Intent(requireContext(), WebViewActivity::class.java).apply {
                        putExtra("url", article.url)
                    }
                    startActivity(intent)
                    return true
                }

                R.id.action_save_news -> {
                    viewModel.saveArticle(article.toArticle())
                    showToast("Статья сохранена")
                    return true
                }

                else -> {}
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onSortSelected(sortCategory: String) {
        viewModel.searchNews(queryStr, sortCategory)
    }
}
