package com.example.feature_news.savedNews


import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.toArticleResponse
import com.example.core.ui.BaseFragment
import com.example.feature_news.R
import com.example.feature_news.databinding.FragmentSavedNewsBinding
import com.example.feature_news.adapters.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : BaseFragment<FragmentSavedNewsBinding, SavedNewsViewModel>(
    R.layout.fragment_saved_news, FragmentSavedNewsBinding::inflate
) {

    private val viewModel: SavedNewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun setView() {
        lifecycleScope.launchWhenStarted {
            viewModel.savedArticles.collect { articles ->
                binding.savedRecycler.layoutManager = LinearLayoutManager(requireContext())

                var newsList = articles.map { it.toArticleResponse() }

                adapter = NewsAdapter(requireContext(), newsList, requireActivity().menuInflater)
                adapter.onItemClick = { article ->
                    var bundle = Bundle()
                    bundle.putString("urlToImage", article.urlToImage)
                    bundle.putString("author", article.author)
                    bundle.putString("content", article.description)


                    findNavController().navigate(R.id.action_savedNewsFragment_to_savedNewsDetailsFragment, bundle)}
                binding.savedRecycler.adapter = adapter


            }
        }
    }
}